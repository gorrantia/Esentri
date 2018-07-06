package ch.rhb.integra;

import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Reads version information from the manifest file. The version information can be printed as
 * simple string value. Another possibility is to display the complete information within a AWT
 * dialog.
 *
 * @author <a href="mailto:markus.lohn@esentri.com">Markus Lohn</a>
 */
public final class VersionInfo {

  /**
   * The name of the package where this class belongs to.
   */
  private static String packageName;

  /**
   * The implementation title.
   */
  private String implementationTitle;

  /**
   * The implementation vendor.
   */
  private String implementationVendor;

  /**
   * The implementation version.
   */
  private String implementationVersion;

  /**
   * The specification title.
   */
  private String specificationTitle;

  /**
   * The specification vendor.
   */
  private String specificationVendor;

  /**
   * The specification version.
   */
  private String specificationVersion;

  /**
   * The name of the operating system.
   */
  private String operatingSystem;

  /**
   * The version of the operating system.
   */
  private String operationSystemVersion;

  /**
   * The version of JAVA.
   */
  private String javaVersion;

  /**
   * The name of the vendor of the JVM.
   */
  private String javaVendor;

  /**
   * Constructs a new <code>VersionInfo</code> object.
   */
  public VersionInfo() {
    try {
      initializeVersionInformationByManifest();
    } catch (Exception ex) {
      initializeVersionInformationByPackage();
    }

    initializeJavaAndOsParameters();
  }

  /**
   * Gets the version number as <code>String</code> object.
   *
   * @return version number, like "1.4.0.2"
   */
  public String getVersionNumberString() {
    String version = "0.0.0.0";

    if (implementationVersion != null) {
      version = implementationVersion;
    } else if (specificationVersion != null) {
      version = specificationVersion;
    }
    return version;
  }

  /**
   * Gets the title.
   * 
   * @return a title
   */
  public String getTitle() {
    return implementationTitle;
  }

  /**
   * Provides the version information as "HTML-String".
   *
   * @return version information as HTML
   */
  public String toHtml() {
    String versionInformation = getVersionInformation();
    return versionInformation.replaceAll("\n", "<br>");
  }

  /**
   * Prints versioning information to the standard output device.
   */
  public void printToStdout() {
    System.out.print(getVersionInformation());
  }

  /**
   * Prints the versioning information to a AWT frame window.
   */
  public void printToWindow() {
    final Frame frame = new Frame(packageName);
    frame.setLayout(null);
    frame.setSize(new Dimension(400, 300));
    frame.setBackground(SystemColor.control);
    frame.setResizable(false);
    frame.setSize(frame.getPreferredSize());
    Button button1 = new Button();
    button1.setLabel("OK");
    button1.setBounds(new Rectangle(163, 240, 68, 23));
    button1.addActionListener(new CloseActionListener(frame));
    TextArea textArea1 = new TextArea();
    textArea1.setBounds(new Rectangle(10, 40, 375, 185));
    textArea1.setEditable(false);
    textArea1.setText(getVersionInformation());
    frame.add(textArea1, null);
    frame.add(button1, null);

    frame.setVisible(true);
  }

  @Override
  public String toString() {
    return getVersionInformation();
  }

  private String getVersionInformation() {
    StringBuilder sbuf = new StringBuilder();

    sbuf.append("Package Name: ").append(packageName);
    sbuf.append("\n");
    sbuf.append("Implementation Title: ");
    if (this.implementationTitle != null) {
      sbuf.append(this.implementationTitle);
    } else {
      sbuf.append("n/a");
    }
    sbuf.append("\n");

    sbuf.append("Implementation Vendor: ");
    if (this.implementationVendor != null) {
      sbuf.append(this.implementationVendor);
    } else {
      sbuf.append("n/a");
    }
    sbuf.append("\n");

    sbuf.append("Implementation Version: ");
    if (this.implementationVersion != null) {
      sbuf.append(this.implementationVersion);
    } else {
      sbuf.append("n/a");
    }
    sbuf.append("\n");

    sbuf.append("Specification Title: ");
    if (this.specificationTitle != null) {
      sbuf.append(this.specificationTitle);
    } else {
      sbuf.append("n/a");
    }
    sbuf.append("\n");

    sbuf.append("Specification Vendor: ");
    if (this.specificationVendor != null) {
      sbuf.append(this.specificationVendor);
    } else {
      sbuf.append("n/a");
    }
    sbuf.append("\n");

    sbuf.append("Specification Version: ");
    if (this.specificationVersion != null) {
      sbuf.append(this.specificationVersion);
    } else {
      sbuf.append("n/a");
    }
    sbuf.append("\n");

    sbuf.append("Operating System: ");
    sbuf.append(this.operatingSystem);
    sbuf.append(" Version: ");
    sbuf.append(this.operationSystemVersion);
    sbuf.append("\n");

    sbuf.append("Java: ");
    sbuf.append(this.javaVendor);
    sbuf.append(" Version: ");
    sbuf.append(this.javaVersion);
    sbuf.append("\n");

    return sbuf.toString();
  }

  /**
   * Displays the versioning information.
   *
   * @param as the arguments
   */
  public void show(String[] as) {
    if (as.length > 0 && as[0].equalsIgnoreCase("-stdout")) {
      printToStdout();
    } else {
      printToWindow();
    }
  }

  /**
   * The main function for this object.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    new VersionInfo().show(args);
  }

  private void initializeJavaAndOsParameters() {
    operatingSystem = System.getProperty("os.name");
    operationSystemVersion = System.getProperty("os.version");
    javaVersion = System.getProperty("java.version");
    javaVendor = System.getProperty("java.vendor");
  }

  private void initializeVersionInformationByPackage() {
    packageName = VersionInfo.class.getPackage().getName();
    Package pkg = Package.getPackage(packageName);

    implementationTitle = pkg.getImplementationTitle();
    implementationVendor = pkg.getImplementationVendor();
    implementationVersion = pkg.getImplementationVersion();

    specificationTitle = pkg.getImplementationTitle();
    specificationVendor = pkg.getSpecificationVendor();
    specificationVersion = pkg.getSpecificationVersion();
  }

  private void initializeVersionInformationByManifest() throws MalformedURLException, IOException {
    packageName = VersionInfo.class.getPackage().getName();
    String clz = getClass().getSimpleName() + ".class";
    String pth = getClass().getResource(clz).toString();
    String mnf = pth.substring(0, pth.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";

    URL url = new URL(mnf);
    Manifest manifest = new Manifest(url.openStream());
    Attributes attrs = manifest.getMainAttributes();

    implementationTitle = attrs.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
    implementationVendor = attrs.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
    implementationVersion = attrs.getValue(Attributes.Name.IMPLEMENTATION_VERSION);

    specificationTitle = attrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
    specificationVendor = attrs.getValue(Attributes.Name.SPECIFICATION_VENDOR);
    specificationVersion = attrs.getValue(Attributes.Name.SPECIFICATION_VERSION);
  }

  private static class CloseActionListener implements ActionListener {

    /**
     * Reference to the frame to close.
     */
    private Frame frame = null;

    public CloseActionListener(Frame frame) {
      this.frame = frame;
    }

    public void actionPerformed(ActionEvent event) {
      frame.dispose();
    }

  }
}

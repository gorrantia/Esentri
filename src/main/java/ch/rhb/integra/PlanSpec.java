package ch.rhb.integra;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.AtlassianModule;
import com.atlassian.bamboo.specs.api.builders.BambooKey;
import com.atlassian.bamboo.specs.api.builders.BambooOid;
import com.atlassian.bamboo.specs.api.builders.Variable;
import com.atlassian.bamboo.specs.api.builders.applink.ApplicationLink;
import com.atlassian.bamboo.specs.api.builders.notification.Notification;
import com.atlassian.bamboo.specs.api.builders.permission.PermissionType;
import com.atlassian.bamboo.specs.api.builders.permission.Permissions;
import com.atlassian.bamboo.specs.api.builders.permission.PlanPermissions;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.PlanBranchIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.PlanIdentifier;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchCleanup;
import com.atlassian.bamboo.specs.api.builders.plan.branches.BranchIntegration;
import com.atlassian.bamboo.specs.api.builders.plan.branches.PlanBranchManagement;
import com.atlassian.bamboo.specs.api.builders.plan.configuration.ConcurrentBuilds;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.api.builders.repository.VcsChangeDetection;
import com.atlassian.bamboo.specs.api.builders.requirement.Requirement;
import com.atlassian.bamboo.specs.api.builders.task.AnyTask;
import com.atlassian.bamboo.specs.builders.notification.CommittersRecipient;
import com.atlassian.bamboo.specs.builders.notification.PlanFailedNotification;
import com.atlassian.bamboo.specs.builders.notification.ResponsibleRecipient;
import com.atlassian.bamboo.specs.builders.repository.bitbucket.server.BitbucketServerRepository;
import com.atlassian.bamboo.specs.builders.repository.viewer.BitbucketServerRepositoryViewer;
import com.atlassian.bamboo.specs.builders.task.CheckoutItem;
import com.atlassian.bamboo.specs.builders.task.CommandTask;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import com.atlassian.bamboo.specs.builders.trigger.BitbucketServerTrigger;
import com.atlassian.bamboo.specs.util.BambooServer;
import com.atlassian.bamboo.specs.util.MapBuilder;

/**
 * Creates or updates a build plan for this project in Bamboo.
 *
 * Use mvn -Ppublish-specs to publish the plan to Bamboo.
 */
@BambooSpec
public class PlanSpec {

	/*
	 * The unique identifier of the Bamboo project.
	 */
	public final String projectKey = "MID";
	/*
	 * The name of the Bamboo project.
	 */
	public final String projectName = "Middleware";
	/*
	 * The description of the Bamboo project.
	 */
	public final String projectDescription = "Contains integration projects based on Red Hat Technologies.";
	/*
	 * The name for the build plan.
	 */
	public final String planName = "mitarbeiter-sync";
	/*
	 * The unique identifier of the build plan based on the planName.
	 */
	public final String planKey = planName.substring(0, 3).toUpperCase();

	/*
	 * The unique of the OpenShift project (kubernetes namespace).
	 */
	public final String openshiftProjectName = "esentri-test";

    public Project getProject() {
        final Project project = new Project()
            .key(projectKey)
            .name(projectName)
            .description(projectDescription);
        return project;
    }

    public Plan createPlan() {
        final Project project = getProject();

        final Plan plan = new Plan(project, planName, planKey)
            .description("Plan to build and deploy mitarbeiter-sync to OpenShift.")
            .pluginConfigurations(new ConcurrentBuilds()
                    .useSystemWideDefault(false))
            .stages(new Stage("Build and Test")
                    .jobs(new Job("Normal Build",
                            new BambooKey("JOB1"))
                            .description("Kompiliert und tested das Projekt.")
                            .tasks(new VcsCheckoutTask()
                                    .description("Checkout Source from Repository")
                                    .checkoutItems(new CheckoutItem().defaultRepository()),
                                new AnyTask(new AtlassianModule("com.davidehringer.atlassian.bamboo.maven.maven-pom-parser-plugin:maven-pom-parser-plugin"))
                                    .description("Extract Project Metadata")
                                    .configuration(new MapBuilder()
                                            .put("projectFile", "")
                                            .put("gavOrCustom", "0")
                                            .put("variableType", "2")
                                            .put("prefixOption", "1")
                                            .put("customVariableName", "")
                                            .put("stripSnapshot", "")
                                            .put("customElement", "")
                                            .put("customPrefix", "")
                                            .build()),
                                new AnyTask(new AtlassianModule("org.jfrog.bamboo.bamboo-artifactory-plugin:maven3Task"))
                                    .description("Build and Test")
                                    .configuration(new MapBuilder()
                                            .put("builder.artifactoryMaven3Builder.projectFile", "")
                                            .put("artifactory.vcs.type", "GIT")
                                            .put("artifactory.vcs.p4.depot", "")
                                            .put("deployMavenArtifacts", "")
                                            .put("bintrayConfiguration", "")
                                            .put("bintray.licenses", "")
                                            .put("builder.artifactoryMaven3Builder.disableAutoLicenseDiscovery", "")
                                            .put("artifactory.common.blackduck.appVersion", "")
                                            .put("artifactory.common.blackduck.includePublishedArtifacts", "")
                                            .put("builder.artifactoryMaven3Builder.ivyPattern", "")
                                            .put("builder.artifactoryMaven3Builder.filterExcludedArtifactsFromBuild", "")
                                            .put("artifactory.vcs.git.username", "")
                                            .put("bintray.signMethod", "false")
                                            .put("runLicenseChecks", "")
                                            .put("builder.artifactoryMaven3Builder.vcsTagBase", "")
                                            .put("builder.artifactoryMaven3Builder.alternativeTasks", "")
                                            .put("bintray.subject", "")
                                            .put("builder.artifactoryMaven3Builder.artifactPattern", "")
                                            .put("builder.artifactoryMaven3Builder.resolutionRepo", "libs-release")
                                            .put("builder.artifactoryMaven3Builder.deployableRepo", "libs-snapshot-local")
                                            .put("builder.artifactoryMaven3Builder.goal", "clean test")
                                            .put("envVarsIncludePatterns", "")
                                            .put("builder.artifactoryMaven3Builder.gitReleaseBranch", "REL-BRANCH-")
                                            .put("builder.artifactoryMaven3Builder.runLicenseChecks", "")
                                            .put("useM2CompatiblePatterns", "")
                                            .put("builder.artifactoryMaven3Builder.additionalMavenParams", "")
                                            .put("builder.artifactoryMaven3Builder.testChecked", "")
                                            .put("bintray.packageName", "")
                                            .put("artifactory.common.blackduck.scopes", "")
                                            .put("artifactory.vcs.p4.port", "")
                                            .put("artifactory.common.blackduck.runChecks", "")
                                            .put("builder.artifactoryMaven3Builder.publishMavenDescriptors", "")
                                            .put("builder.artifactoryMaven3Builder.enableReleaseManagement", "")
                                            .put("builder.artifactoryMaven3Builder.limitChecksToScopes", "")
                                            .put("builder.artifactoryMaven3Builder.deployMavenArtifacts", "")
                                            .put("envVarsExcludePatterns", "*password*,*secret*,*security*,*key*")
                                            .put("artifactory.vcs.p4.password", "/* SENSITIVE INFORMATION */")
                                            .put("artifactory.vcs.git.password", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.licenseViolationRecipients", "")
                                            .put("resolveFromArtifacts", "true")
                                            .put("artifactory.vcs.git.ssh.key", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.workingSubDirectory", "")
                                            .put("recordAllDependencies", "")
                                            .put("bintray.repository", "")
                                            .put("artifactory.vcs.p4.client", "")
                                            .put("artifactory.vcs.git.url", "")
                                            .put("builder.artifactoryMaven3Builder.publishIvyDescriptors", "")
                                            .put("artifactory.vcs.p4.username", "")
                                            .put("builder.artifactoryMaven3Builder.publishBuildInfo", "")
                                            .put("publishBuildInfo", "true")
                                            .put("builder.artifactoryMaven3Builder.mavenOpts", "")
                                            .put("artifactory.vcs.git.authenticationType", "NONE")
                                            .put("builder.artifactoryMaven3Builder.executable", "Artifactory Maven 3")
                                            .put("builder.artifactoryMaven3Builder.resolverUsername", "bamboo")
                                            .put("builder.artifactoryMaven3Builder.testResultsDirectory", "**/target/surefire-reports/*.xml")
                                            .put("testDirectoryOption", "standardTestDirectory")
                                            .put("artifactory.common.blackduck.autoDiscardStaleComponentRequests", "")
                                            .put("builder.artifactoryMaven3Builder.deployerUsername", "bamboo")
                                            .put("builder.artifactoryMaven3Builder.artifactoryServerId", "0")
                                            .put("builder.artifactoryMaven3Builder.buildJdk", "JDK 1.8.0_161")
                                            .put("artifactory.common.blackduck.autoCreateMissingComponentRequests", "")
                                            .put("builder.artifactoryMaven3Builder.resolutionArtifactoryServerId", "0")
                                            .put("builder.artifactoryMaven3Builder.environmentVariables", "")
                                            .put("builder.artifactoryMaven3Builder.deployExcludePatterns", "")
                                            .put("builder.artifactoryMaven3Builder.includePublishedArtifacts", "")
                                            .put("artifactory.common.blackduck.reportRecipients", "")
                                            .put("builder.artifactoryMaven3Builder.deployIncludePatterns", "")
                                            .put("artifactory.common.blackduck.appName", "")
                                            .put("bintray.mavenSync", "")
                                            .put("testChecked", "true")
                                            .put("includeEnvVars", "")
                                            .put("baseUrl", "http://svcatlassianbamboo01:8085")
                                            .put("builder.artifactoryMaven3Builder.testDirectoryOption", "")
                                            .put("bintray.vcsUrl", "")
                                            .put("artifactory.vcs.git.ssh.passphrase", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.resolverPassword", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.useM2CompatiblePatterns", "")
                                            .put("bintray.gpgPassphrase", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.deployerPassword", "/* SENSITIVE INFORMATION */")
                                            .put("enableReleaseManagement", "")
                                            .build()))
                            .requirements(new Requirement("system.builder.command.OC"))),
                new Stage("Deployment")
                    .manual(true)
                    .jobs(new Job("Deploy to Integration",
                            new BambooKey("DTI"))
                            .artifacts(new Artifact()
                                    .name("bamboo-specs.jar")
                                    .copyPattern("${bamboo.maven.artifactId}-${bamboo.maven.version}.jar")
                                    .location("target")
                                    .shared(true))
                            .tasks(new VcsCheckoutTask()
                                    .description("Checkout Source from Repository")
                                    .checkoutItems(new CheckoutItem().defaultRepository()),
                                new AnyTask(new AtlassianModule("com.davidehringer.atlassian.bamboo.maven.maven-pom-parser-plugin:maven-pom-parser-plugin"))
                                    .description("Extract Project Metadata")
                                    .configuration(new MapBuilder()
                                            .put("projectFile", "")
                                            .put("gavOrCustom", "0")
                                            .put("variableType", "2")
                                            .put("prefixOption", "1")
                                            .put("customVariableName", "")
                                            .put("stripSnapshot", "")
                                            .put("customElement", "")
                                            .put("customPrefix", "")
                                            .build()),
                                new CommandTask()
                                    .description("Login to OpenShift")
                                    .executable("OC")
                                    .argument("login -u ${bamboo.openshiftUser} -p ${bamboo.openshiftPassword} --server=${bamboo.openshiftURL} --insecure-skip-tls-verify"),
                                new CommandTask()
                                    .description("Change Project in OpenShift")
                                    .executable("OC")
                                    .argument("project ${bamboo.openshiftProject}"),
                                new AnyTask(new AtlassianModule("org.jfrog.bamboo.bamboo-artifactory-plugin:maven3Task"))
                                    .description("Package and Deploy")
                                    .configuration(new MapBuilder()
                                            .put("builder.artifactoryMaven3Builder.projectFile", "")
                                            .put("artifactory.vcs.type", "GIT")
                                            .put("artifactory.vcs.p4.depot", "")
                                            .put("deployMavenArtifacts", "true")
                                            .put("bintrayConfiguration", "")
                                            .put("bintray.licenses", "")
                                            .put("builder.artifactoryMaven3Builder.disableAutoLicenseDiscovery", "")
                                            .put("artifactory.common.blackduck.appVersion", "")
                                            .put("artifactory.common.blackduck.includePublishedArtifacts", "")
                                            .put("builder.artifactoryMaven3Builder.ivyPattern", "")
                                            .put("builder.artifactoryMaven3Builder.filterExcludedArtifactsFromBuild", "")
                                            .put("artifactory.vcs.git.username", "")
                                            .put("bintray.signMethod", "false")
                                            .put("runLicenseChecks", "")
                                            .put("builder.artifactoryMaven3Builder.vcsTagBase", "")
                                            .put("builder.artifactoryMaven3Builder.alternativeTasks", "")
                                            .put("bintray.subject", "")
                                            .put("builder.artifactoryMaven3Builder.artifactPattern", "")
                                            .put("builder.artifactoryMaven3Builder.resolutionRepo", "libs-release")
                                            .put("builder.artifactoryMaven3Builder.deployableRepo", "libs-snapshot-local")
                                            .put("builder.artifactoryMaven3Builder.goal", "install fabric8:deploy -Dfabric8.mode=openshift")
                                            .put("envVarsIncludePatterns", "")
                                            .put("builder.artifactoryMaven3Builder.gitReleaseBranch", "REL-BRANCH-")
                                            .put("builder.artifactoryMaven3Builder.runLicenseChecks", "")
                                            .put("useM2CompatiblePatterns", "")
                                            .put("builder.artifactoryMaven3Builder.additionalMavenParams", "")
                                            .put("builder.artifactoryMaven3Builder.testChecked", "")
                                            .put("bintray.packageName", "")
                                            .put("artifactory.common.blackduck.scopes", "")
                                            .put("artifactory.vcs.p4.port", "")
                                            .put("artifactory.common.blackduck.runChecks", "")
                                            .put("builder.artifactoryMaven3Builder.publishMavenDescriptors", "")
                                            .put("builder.artifactoryMaven3Builder.enableReleaseManagement", "")
                                            .put("builder.artifactoryMaven3Builder.limitChecksToScopes", "")
                                            .put("builder.artifactoryMaven3Builder.deployMavenArtifacts", "")
                                            .put("envVarsExcludePatterns", "*password*,*secret*,*security*,*key*")
                                            .put("artifactory.vcs.p4.password", "/* SENSITIVE INFORMATION */")
                                            .put("artifactory.vcs.git.password", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.licenseViolationRecipients", "")
                                            .put("resolveFromArtifacts", "true")
                                            .put("artifactory.vcs.git.ssh.key", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.workingSubDirectory", "")
                                            .put("recordAllDependencies", "")
                                            .put("bintray.repository", "")
                                            .put("artifactory.vcs.p4.client", "")
                                            .put("artifactory.vcs.git.url", "")
                                            .put("builder.artifactoryMaven3Builder.publishIvyDescriptors", "")
                                            .put("artifactory.vcs.p4.username", "")
                                            .put("builder.artifactoryMaven3Builder.publishBuildInfo", "")
                                            .put("publishBuildInfo", "false")
                                            .put("builder.artifactoryMaven3Builder.mavenOpts", "")
                                            .put("artifactory.vcs.git.authenticationType", "NONE")
                                            .put("builder.artifactoryMaven3Builder.executable", "Artifactory Maven 3")
                                            .put("builder.artifactoryMaven3Builder.resolverUsername", "bamboo")
                                            .put("builder.artifactoryMaven3Builder.testResultsDirectory", "**/target/surefire-reports/*.xml")
                                            .put("testDirectoryOption", "standardTestDirectory")
                                            .put("artifactory.common.blackduck.autoDiscardStaleComponentRequests", "")
                                            .put("builder.artifactoryMaven3Builder.deployerUsername", "")
                                            .put("builder.artifactoryMaven3Builder.artifactoryServerId", "0")
                                            .put("builder.artifactoryMaven3Builder.buildJdk", "JDK 1.8.0_161")
                                            .put("artifactory.common.blackduck.autoCreateMissingComponentRequests", "")
                                            .put("builder.artifactoryMaven3Builder.resolutionArtifactoryServerId", "0")
                                            .put("builder.artifactoryMaven3Builder.environmentVariables", "")
                                            .put("builder.artifactoryMaven3Builder.deployExcludePatterns", "")
                                            .put("builder.artifactoryMaven3Builder.includePublishedArtifacts", "")
                                            .put("artifactory.common.blackduck.reportRecipients", "")
                                            .put("builder.artifactoryMaven3Builder.deployIncludePatterns", "")
                                            .put("artifactory.common.blackduck.appName", "")
                                            .put("bintray.mavenSync", "")
                                            .put("testChecked", "true")
                                            .put("includeEnvVars", "")
                                            .put("baseUrl", "http://svcatlassianbamboo01:8085")
                                            .put("builder.artifactoryMaven3Builder.testDirectoryOption", "")
                                            .put("bintray.vcsUrl", "")
                                            .put("artifactory.vcs.git.ssh.passphrase", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.resolverPassword", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.useM2CompatiblePatterns", "")
                                            .put("bintray.gpgPassphrase", "/* SENSITIVE INFORMATION */")
                                            .put("builder.artifactoryMaven3Builder.deployerPassword", "/* SENSITIVE INFORMATION */")
                                            .put("enableReleaseManagement", "")
                                            .build()))),
                new Stage("Post-Deployment")
                    .manual(true)
                    .jobs(new Job("Create Application",
                            new BambooKey("CA"))
                            .tasks(new CommandTask()
                                    .description("Login to OpenShift")
                                    .executable("OC")
                                    .argument("login -u ${bamboo.openshiftUser} -p ${bamboo.openshiftPassword} --server=${bamboo.openshiftURL} --insecure-skip-tls-verify"),
                                new CommandTask()
                                    .description("Change Project in OpenShift")
                                    .executable("OC")
                                    .argument("project ${bamboo.openshiftProject}"),
                                new CommandTask()
                                    .description("Create new application in OpenShift")
                                    .executable("OC")
                                    .argument("new-app --docker-image=docker-registry.default.svc:5000/${bamboo.openshiftProject}/${bamboo.maven.artifactId} --name=${bamboo.maven.artifactId}"))))
            .planRepositories(new BitbucketServerRepository()
                    .name("mitarbeiter-sync (master)")
                    .repositoryViewer(new BitbucketServerRepositoryViewer())
                    .server(new ApplicationLink().name("Bitbucket"))
                    .projectKey(projectKey)
                    .repositorySlug("mitarbeiter-sync")
                    .branch("master")
                    .changeDetection(new VcsChangeDetection()),
                new BitbucketServerRepository()
                    .name("mitarbeiter-sync (develop)")
                    .repositoryViewer(new BitbucketServerRepositoryViewer())
                    .server(new ApplicationLink().name("Bitbucket"))
                    .projectKey(projectKey)
                    .repositorySlug("mitarbeiter-sync")
                    .branch("develop")
                    .changeDetection(new VcsChangeDetection()))
            .triggers(new BitbucketServerTrigger())
            .variables(new Variable("openshiftProject", openshiftProjectName))
            .planBranchManagement(new PlanBranchManagement()
                    .createForVcsBranch()
                    .delete(new BranchCleanup()
                        .whenRemovedFromRepositoryAfterDays(1))
                    .notificationForCommitters()
                    .branchIntegration(new BranchIntegration()
                            .integrationBranch(new PlanBranchIdentifier(new BambooKey(planKey)))
                            .pushOnSuccessfulBuild(true))
                    .issueLinkingEnabled(false))
            .notifications(new Notification()
                    .type(new PlanFailedNotification())
                    .recipients(new ResponsibleRecipient(),
                        new CommittersRecipient()));
        return plan;
    }

    public PlanPermissions planPermission() {
        final PlanPermissions planPermission = new PlanPermissions(new PlanIdentifier(projectKey, planKey))
            .permissions(new Permissions()
                    .groupPermissions("G-SVCATLASSIANBAMBOO-ADMIN", PermissionType.VIEW, PermissionType.ADMIN, PermissionType.EDIT, PermissionType.BUILD, PermissionType.CLONE)
                    .groupPermissions("G-SVCATLASSIANBAMBOO-MIDDLEWARE", PermissionType.BUILD, PermissionType.CLONE, PermissionType.EDIT, PermissionType.VIEW)
                    .loggedInUserPermissions(PermissionType.VIEW, PermissionType.BUILD, PermissionType.CLONE)
                    .anonymousUserPermissionView());
        return planPermission;
    }

	public static void main(String... argv) {
		// By default credentials are read from the '.credentials' file.
		BambooServer bambooServer = new BambooServer("http://svcatlassianbamboo01:8085");
		final PlanSpec planSpec = new PlanSpec();

		final Plan plan = planSpec.createPlan();
		bambooServer.publish(plan);

		final PlanPermissions planPermission = planSpec.planPermission();
		bambooServer.publish(planPermission);
	}
}

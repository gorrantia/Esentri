package ch.rhb.integra.mitarbeiter.psipenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

@XmlType
public class PsipentaRecordWrapper{
    /**
     * <p>
     * Here is the tricky part:
     * <ul>
     *  <li>When this <code>SysParamEntriesWrapper</code> is created by yourself, objects 
     * stored in this <code>entries</code> list is of type SystemParamEntry</li>
     *  <li>Yet during the unmarshalling process, this <code>SysParamEntriesWrapper</code> is 
     * created by the JAXBContext, thus objects stored in the <code>entries</code> is 
     * of type Element actually.</li>
     * </ul>
     * </p>
     */
    List<JAXBElement<PsipentaRecordEntry>> entries = new ArrayList<>();
    public PsipentaRecordWrapper(){
    }
    public void addEntry(String name, String value, String attr){
        addEntry(new PsipentaRecordEntry(name, value, attr));
    }
    public void addEntry(PsipentaRecordEntry entry){
        JAXBElement<PsipentaRecordEntry> bean = new JAXBElement<PsipentaRecordEntry>(new QName("", entry.getName()), PsipentaRecordEntry.class, entry);
        entries.add(bean);
    }
    @XmlAnyElement
    public List<JAXBElement<PsipentaRecordEntry>> getEntries() {
        return entries;
    }
    public void setEntries(List<JAXBElement<PsipentaRecordEntry>> entries) {
        this.entries = entries;
    }
    @Override
    public String toString() {
        return "RecordEntryWrapper [entries=" + toMap() + "]";
    }
    public Map<String, PsipentaRecordEntry> toMap(){
        Map<String, PsipentaRecordEntry> retval = new HashMap<>();
        List<?> entries = this.entries;
        entries.stream().map(PsipentaRecordWrapper::convertToParamEntry).
            forEach(entry -> retval.put(entry.getName(), entry));;
        return retval;
    }
    private static PsipentaRecordEntry convertToParamEntry(Object entry){
        String name = extractName(entry);
        String attr = extractAttr(entry);
        String value = extractValue(entry);
        return new PsipentaRecordEntry(name, value, attr);
    }
    @SuppressWarnings("unchecked")
    private static String extractName(Object entry){
        return extractPart(entry, nameExtractors).orElse("");
    }
    @SuppressWarnings("unchecked")
    private static String extractAttr(Object entry){
        return extractPart(entry, attrExtractors).orElse("");
    }
    @SuppressWarnings("unchecked")
    private static String extractValue(Object entry){
        return extractPart(entry, valueExtractors).orElse("");
    }
    private static <ObjType, RetType> Optional<RetType> extractPart(ObjType obj, Map<Class<?>,
            Function<? super ObjType, RetType>> extractFuncs ){
        for(Class<?> clazz : extractFuncs.keySet()){
            if(clazz.isInstance(obj)){
                return Optional.ofNullable(extractFuncs.get(clazz).apply(obj));
            }
        }
        return Optional.empty();
    }
    private static Map<Class<?>, Function<? super Object, String>> nameExtractors = new HashMap<>();
    private static Map<Class<?>, Function<? super Object, String>> attrExtractors = new HashMap<>();
    private static Map<Class<?>, Function<? super Object, String>> valueExtractors = new HashMap<>();
    static{
        nameExtractors.put(JAXBElement.class, jaxb -> ((JAXBElement<PsipentaRecordEntry>)jaxb).getName().getLocalPart());
//        nameExtractors.put(Element.class, ele -> ((Element) ele).getLocalName());
        attrExtractors.put(JAXBElement.class, jaxb -> ((JAXBElement<PsipentaRecordEntry>)jaxb).getValue().getAttr());
//        attrExtractors.put(Element.class, ele -> ((Element) ele).getAttribute("attr"));
        valueExtractors.put(JAXBElement.class, jaxb -> ((JAXBElement<PsipentaRecordEntry>)jaxb).getValue().getValue());
//        valueExtractors.put(Element.class, ele -> ((Element) ele).getTextContent());
    }
}

package ch.rhb.integra.mitarbeiter.mia;


import ch.rhb.integra.mitarbeiter.mia.jaxb.Anschrift;
import ch.rhb.integra.mitarbeiter.mia.jaxb.AnschriftArt;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Anschriften;
import ch.rhb.integra.mitarbeiter.mia.jaxb.AustrittsGrund;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Familienstand;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Funktionsgruppe;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Geschlecht;
import ch.rhb.integra.mitarbeiter.mia.jaxb.KaderStruktur;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Kostenstelle;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Kostenstellen;
import ch.rhb.integra.mitarbeiter.mia.jaxb.MIA;
import ch.rhb.integra.mitarbeiter.mia.jaxb.MaVersion;
import ch.rhb.integra.mitarbeiter.mia.jaxb.PLZOrt;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Person;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Personal;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Vertragsart;
import ch.rhb.integra.mitarbeiter.mia.jaxb.Zivilstand;
import ch.rhb.integra.mitarbeiter.performis.Address;
import ch.rhb.integra.mitarbeiter.performis.CommAddress;
import ch.rhb.integra.mitarbeiter.performis.Mitarbeiter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("miaTransformer")
@Slf4j
public class MIATransformer {

  private static final String RHB="Rhätische Bahn AG";
  private static final int ADDRESS_TYPE_PRIVATE=1;
 private static final int ADDRESS_TYPE_WORK=2;
  private static final long COMM_ADDRESS_TELEFON=3320001;
  private static final long COMM_ADDRESS_MOBILE=3320003;
  private static final long COMM_ADDRESS_EMAIL=3320005;
  
  public List<MIA> transform(Mitarbeiter[] mitarbeitern){
    List<MIA> miaList=new ArrayList<>();
    for(Mitarbeiter mit:mitarbeitern) {
      if(transferMitarbeiter(mit)) {
        MIA mia= new MIA();
        mia.setPerson(transformPerson(mit));
        miaList.add(mia);
      }
    }
    return miaList;
  }
  
  private boolean transferMitarbeiter(Mitarbeiter mit) {
    boolean result= false;
    if(mit.getArbeitgeberAddress() !=null && mit.getArbeitgeberAddress().length>0 && 
        mit.getArbeitgeberAddress()[0].getName()!=null && mit.getArbeitgeberAddress()[0].getName().equals(RHB) ) {
      result=true;
    }
    return result;
  }
  
  private Person transformPerson(Mitarbeiter mit) {
    Person person= new Person();
    person.setGeburtsdatum(parseAndFormatDate(mit.getGeburstDatum()));
    if(mit.getPersonal()!=null) {
      person.setPersonID(mit.getPersonal().getPersonalID());
    }
    person.setMitarbeiter(transformMitarbeiter(mit));
    return person;
  }
  
  private ch.rhb.integra.mitarbeiter.mia.jaxb.Mitarbeiter transformMitarbeiter(Mitarbeiter mit) {
    ch.rhb.integra.mitarbeiter.mia.jaxb.Mitarbeiter mitarbeiter=new ch.rhb.integra.mitarbeiter.mia.jaxb.Mitarbeiter();
    mitarbeiter.setFirmaNr(convertString(mit.getFirmanr()));
    mitarbeiter.setPersonalNummer(mit.getPersNr());
    mitarbeiter.setEintritt(parseAndFormatDate(mit.getEintritt()));
    mitarbeiter.setAustritt(parseAndFormatDate(mit.getAustritt()));
    mitarbeiter.setMaVersion(transformMaVersion(mit));
    return mitarbeiter;
  }
  
  private MaVersion transformMaVersion(Mitarbeiter mit) {
    MaVersion maVersion= new MaVersion();
    maVersion.setMaVersionId(mit.getMaVersionID());
    maVersion.setMaVersionGueltigAb(parseAndFormatDate(mit.getVersionGueltigAb()));
    maVersion.setName(mit.getNachname());
    maVersion.setVorname(mit.getVorname());
    maVersion.setPersonal(transformPersonal(mit));
    maVersion.setAnschriften(transformAnschriften(mit));
    maVersion.setKostenstellen(transformKostenstellen(mit));
    return maVersion;
  }
  
  private Kostenstellen transformKostenstellen(Mitarbeiter mit) {
    Kostenstellen kostenstellen=new Kostenstellen();
    List<Kostenstelle> kostenstelleList= new ArrayList<>();
    kostenstellen.setKostenstelle(kostenstelleList);
    Kostenstelle kostenstelle= new Kostenstelle();
    kostenstelle.setKostenstelle(convertString(mit.getKstnr()));
    return kostenstellen;
  }
  private Anschriften transformAnschriften(Mitarbeiter mit) {
    Anschriften anschriften= new Anschriften();
    List<Anschrift> anschiftList= new ArrayList<>();
    if(mit.getMitarbeiterAddress()!=null && mit.getMitarbeiterAddress().length>0) {
      anschiftList.add(transformAnschrift(mit.getMitarbeiterAddress()[0],ADDRESS_TYPE_PRIVATE,mit));
    }
    if(mit.getFirmaAddress()!=null && mit.getFirmaAddress().length>0) {
      anschiftList.add(transformAnschrift(mit.getFirmaAddress()[0],ADDRESS_TYPE_WORK,mit));
    }
    anschriften.setAnschrift(anschiftList);
    return anschriften;
  }
  
  private Anschrift transformAnschrift(Address address,int adressType,Mitarbeiter mit) {
    Anschrift anschrift= new Anschrift();
    if(address.getCodemaster()!=null && address.getCodemaster().length>0) {
      anschrift.setIsoLandCode( address.getCodemaster()[0].getShortText());
    }
    anschrift.setStrasse(address.getStreet());
    anschrift.setHausNummer(address.getHouseNO());
    anschrift.setPostfach(address.getPoBox());
    anschrift.setPlzOrt(new PLZOrt(convertString(mit.getPlz()),mit.getOrt()));
    if(adressType ==ADDRESS_TYPE_PRIVATE) {
      anschrift.setBundesland(mit.getKanton());
      anschrift.setAnschriftArt(new AnschriftArt("P","privat"));
    } else {
      if(mit.getPersonal()!=null && mit.getPersonal().getCodemaster()!=null && mit.getPersonal().getCodemaster().length>0) {
        anschrift.setBundesland(mit.getPersonal().getCodemaster()[0].getShortText());
      }
      anschrift.setAnschriftArt(new AnschriftArt("D","Dienstanschrift"));
    }
    if(address.getCommAddress()!=null) {
      for(CommAddress commAddress:address.getCommAddress()) {
        if(commAddress.getC332CommType() == COMM_ADDRESS_TELEFON) {
          anschrift.setTelefon(commAddress.getCommAddress());
        }
        if(commAddress.getC332CommType() == COMM_ADDRESS_MOBILE) {
          anschrift.setMobile(commAddress.getCommAddress());
          
        }
        if(commAddress.getC332CommType() == COMM_ADDRESS_EMAIL) {
          anschrift.setEmail(commAddress.getCommAddress());
          
        }
      }
    }
   
    return anschrift;
  }
  
  private Personal transformPersonal(Mitarbeiter mit) {
    Personal personal= new Personal();
    personal.setSozialVersicherungsNummer(mit.getSozialVersicherungsNummer());
    if(mit.getPersonal()!=null) {
      personal.setHeimatOrt(mit.getPersonal().getHeimatort());
      personal.setStaatsangehoerigkeit(mit.getPersonal().getNationalitaetKurz());
      personal.setGeschlecht(new Geschlecht(mit.getPersonal().getGeschlechtCode(),mit.getPersonal().getGeschlecht()));
    }
    personal.setBeschaeftigungsGrad(convertString(mit.getBeschaeftigungsGrad()));
    personal.setBeruf(mit.getBerufErlernt());
    personal.setExterneBerufsbezeichnung(mit.getBerufAusgeubt());
    personal.setFamilienstand(transformFamilienstand(mit));
    personal.setVertragsart(new Vertragsart(convertString(mit.getFirmenArbeitsVertragCode()),mit.getFirmenArbeitsVertrag()));
    personal.setAusttritsgrund(new AustrittsGrund(convertString(mit.getAustrittsGrundCode()),mit.getAustritt()));
    personal.setKaderStruktur(new KaderStruktur(convertString(mit.getStrukturCode()),mit.getStruktur()));
    personal.setFunktionsgruppe(new Funktionsgruppe(convertString(mit.getFunktionsGruppeCode()),mit.getFunktionsGruppe()));
    return personal;
  }
  
  private Familienstand transformFamilienstand(Mitarbeiter mit) {
    Familienstand fst= new Familienstand();
    if(mit.getPersonal()!=null) {
      fst.setZivilstandDatumVon(parseAndFormatDate(mit.getPersonal().getZivilstandDatumVon()));
      fst.setZivilstand(new Zivilstand(mit.getPersonal().getZivilstandCode(),mit.getPersonal().getZivilstand()));
    }
    
    return fst;
  }
  
  private Long convertString(String value) {
    Long response=new Long(0);
    if(value == null || value.isEmpty()) {
      return response;
    }
    try {
      response=Long.parseLong(value);
    }catch(NumberFormatException ex) {
      log.warn("Could not cast " +value+ " into Long. 0 will be returned", ex.getMessage());
    }
    return response;
  } 
  private String parseAndFormatDate(String stringDate) {
    String result= "";
    if(stringDate==null || stringDate.isEmpty()) {
      return result;
    }
    SimpleDateFormat source = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat target = new SimpleDateFormat("yyyy-MM-dd");
    try {
      java.util.Date date = source.parse(stringDate);
      result=target.format(date);
    } catch (ParseException e) {
      log.warn("Could not cast " +stringDate+ " into SQL Date." , e.getMessage());
      throw new RuntimeException(e);
    }
    return result;
  }
}

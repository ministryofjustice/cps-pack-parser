package uk.gov.justice.digital.cpspackparser.parser;

import org.junit.Before;
import org.junit.Test;
import uk.gov.justice.digital.cpspackparser.data.api.ConvictionGroup;

import static org.assertj.core.api.Assertions.assertThat;

public class CPSPackParserTest {
    private CPSPackParser parser;

    @Before
    public void before() {
        parser = new CPSPackParser();
    }

    //Optional[CPSPack(defendantId=DefendantId(croNumber=12345/12W, pncNumber=18/123456Z), convictionSummary=ConvictionSummary(convictionCount=20, offenceCount=100, dateOfFirstConviction=23/01/05, dateOfLastConviction=22/12/18, summary=[ConvictionGroup(count=1, description=OFFENCES AGAINST THE PERSON, yearDates=(2017) ), ConvictionGroup(count=2, description=OFFENCES AGAINST PROPERTY, yearDates=(2005-2017) ), ConvictionGroup(count=3, description=THEFT AND KINDRED OFFENCES, yearDates=(2005-2011) ), ConvictionGroup(count=4, description=PUBLIC DISORDER OFFENCES, yearDates=(2007-2017) ), ConvictionGroup(count=12, description=OFFENCES RELATING TO POLICE/COURTS/PRISONS, yearDates=(2005-2018) ), ConvictionGroup(count=13, description=MISCELLANEOUS OFFENCES, yearDates=(2005-2018) )]))]

    @Test
    public void extractsCroNumberFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getDefendantId().getCroNumber())
                .isEqualTo("12345/12W");

    }


    @Test
    public void extractsPncNumberFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getDefendantId().getPncNumber())
                .isEqualTo("18/123456Z");

    }

    @Test
    public void extractsConvictionCountFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getConvictionSummary().getConvictionCount())
                .isEqualTo(20);

    }

    @Test
    public void extractsOffenceCountFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getConvictionSummary().getOffenceCount())
                .isEqualTo(100);

    }

    @Test
    public void extractsDateOfFirstConvictionFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getConvictionSummary().getDateOfFirstConviction())
                .isEqualTo("23/01/05");

    }
    @Test
    public void extractsDateOfLastConvictionFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getConvictionSummary().getDateOfLastConviction())
                .isEqualTo("22/12/18");

    }

    @Test
    public void extractsConvictionSummaryFromPreConvictions() {
        assertThat(parser.extractCPSPack(aCpsPack()).orElseThrow(this::error).getConvictionSummary().getSummary())
                .hasSize(6)
                .contains(ConvictionGroup
                        .builder()
                        .yearDates("(2005-2018) ")
                        .description("OFFENCES RELATING TO POLICE/COURTS/PRISONS")
                        .count(12)
                        .build());


    }


    private AssertionError error() {
        return new AssertionError("No pack found");
    }
    private String aCpsPack() {
        return "CROWN PROSECUTION SERVICE\n" +
                "Regina \n" +
                "v\n" +
                "Johnny Defendant \n" +
                "Key Witness Statements\n" +
                "STATEMENT/EVIDENCE LIST\n" +
                "URN: 50GT1234567\n" +
                "Regina \n" +
                "v\n" +
                "Johnny Defendant \n" +
                "No Statement(s), deposition(s) and document(s) (other than exhibits) Statement Date Page\n" +
                "CROWN PROSECUTION SERVICE\n" +
                "Regina \n" +
                "v\n" +
                "Johnny Defendant \n" +
                "Key Exhibits\n" +
                "LIST OF EXHIBITS\n" +
                "URN: 50GT1234567\n" +
                "Regina \n" +
                "v\n" +
                "Johnny Defendant \n" +
                "No Exhibit Ref No Description Page\n" +
                "CROWN PROSECUTION SERVICE\n" +
                "Regina \n" +
                "v\n" +
                "Johnny Defendant \n" +
                "Pre Cons\n" +
                "    23/12/18 12:14 \n" +
                "  \n" +
                "   THIS PRINTOUT IS PRODUCED FOR THE USE OF THE COURT, DEFENCE AND PROBATION \n" +
                "           SERVICE ONLY AND MUST NOT BE DISCLOSED TO ANY OTHER PARTY \n" +
                "  \n" +
                "                          DATA PROTECTION LEGISLATION \n" +
                "  THESE PERSONAL DATA ARE PROVIDED TO YOU FOR THE AGREED SPECIFIED PURPOSE(S). \n" +
                "   KEEP THE DATA SECURE AND PROTECT THEM AGAINST LOSS OR UNAUTHORISED ACCESS. \n" +
                "                          --------------------------- \n" +
                "  \n" +
                "  \n" +
                "                   ******************************************** \n" +
                "                   *                                          * \n" +
                "                   *                                          * \n" +
                "                   *      COURT/DEFENCE/PROBATION PRINT       * \n" +
                "                   *                                          * \n" +
                "                   *                                          * \n" +
                "                   ******************************************** \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                    PRINT OF PNC RECORD - PNCID : 18/123456Z \n" +
                "                    ---------------------------------------- \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "       PRINT FOR : SHEFFIELD AND LEEDS POLICE                        0     \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                   TOTAL NUMBER OF PAGES ATTACHED  18 \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "           PLEASE NOTE THAT IN THE ABSENCE OF FINGERPRINTS, IDENTITY \n" +
                "         CANNOT BE POSITIVELY CONFIRMED WITH THE SUBJECT OF YOUR ENQUIRY \n" +
                "             AND YOU SHOULD CONFIRM THE INFORMATION WITH THE PERSON \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                        PAGE 1   OF 18 \n" +
                "  \n" +
                "    THIS PRINTOUT IS PRODUCED FOR THE USE OF THE COURT, DEFENCE AND PROBATION \n" +
                "            SERVICE ONLY AND MUST NOT BE DISCLOSED TO ANY OTHER PARTY \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                          DATA PROTECTION LEGISLATION \n" +
                "  THESE PERSONAL DATA ARE PROVIDED TO YOU FOR THE AGREED SPECIFIED PURPOSES. \n" +
                "  KEEP THE DATA SECURE AND PROTECT THEM AGAINST LOSS OR UNAUTHORISED ACCESS. \n" +
                "                          --------------------------- \n" +
                "  \n" +
                "  SURNAME      : DEFENDANT \n" +
                "  FORENAME(S)  : JOHNNY \n" +
                "  BORN         : 23/12/18  ROTHERHAM \n" +
                "  ADDRESS      : 3 BUDDY NORTH \n" +
                "                 BIRMINGHAM \n" +
                "                 SHEFFIELD \n" +
                "                 S1 2BJ \n" +
                "  \n" +
                "  DRIVER NO    : DEFENDANT/1234567/G77HH  CRO NO : 12345/12W     PNCID : 18/123456Z \n" +
                "  \n" +
                "  ALIAS NAMES  (1) \n" +
                "  \n" +
                "    1 DEFENDANT, JOHNNY LEE \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "              SUMMARY OF CONVICTIONS, REPRIMANDS/WARNINGS/CAUTIONS \n" +
                "               AND NORTHERN IRELAND NON-COURT DISPOSALS (NI NCD) \n" +
                "              ---------------------------------------------------- \n" +
                "  \n" +
                "        CONVICTION(S)  :    20                      OFFENCE(S)  :    100 \n" +
                "  \n" +
                "      DATE FIRST CONVICTED :  23/01/05    DATE LAST CONVICTED :  22/12/18 \n" +
                "  \n" +
                "              1 OFFENCES AGAINST THE PERSON                 (2017) \n" +
                "              2 OFFENCES AGAINST PROPERTY                   (2005-2017) \n" +
                "              3 THEFT AND KINDRED OFFENCES                  (2005-2011) \n" +
                "              4 PUBLIC DISORDER OFFENCES                    (2007-2017) \n" +
                "             12 OFFENCES RELATING TO POLICE/COURTS/PRISONS  (2005-2018) \n" +
                "             13 MISCELLANEOUS OFFENCES                      (2005-2018) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "                         NO REPRIMANDS/WARNINGS/CAUTIONS \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "               NO NORTHERN IRELAND NON-COURT DISPOSALS (NI NCD) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  END OF SUMMARY OF CONVICTIONS, REPRIMANDS/WARNINGS/CAUTIONS AND NI NCD \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 2   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "                                   CONVICTION(S) \n" +
                "                                   ------------- \n" +
                "  \n" +
                "  1.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  MAKING OFF WITHOUT PAYING                REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1978 s.3 \n" +
                "     **  OFFENCE COMMITTED ON BAIL  ** \n" +
                "  \n" +
                "     2.  TAKING MOTOR VEHICLE WITHOUT CONSENT     REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1968 s.12(1) \n" +
                "     **  OFFENCE COMMITTED ON BAIL  ** \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  2.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  TAKING MOTOR VEHICLE WITHOUT CONSENT     REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1968 s.12(1) \n" +
                "  \n" +
                "     2.  BURGLARY AND THEFT - DWELLING            REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1968 s.9(1)(b) \n" +
                "  \n" +
                "     3.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REFERRAL ORDER 12 MTHS \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "     4.  USING VEHICLE WHILE UNINSURED            REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     DRIVING LICENCE ENDORSED \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2) \n" +
                "  \n" +
                "     5.  DRIVING OTHERWISE THAN IN ACCORDANCE     REFERRAL ORDER 12 MTHS \n" +
                "         WITH A LICENCE                           DRIVING LICENCE ENDORSED \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         ROAD TRAFFIC ACT 1988 s.87(1) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  3.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REFERRAL ORDER 12 MTHS \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "     2.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REFERRAL ORDER 12 MTHS \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 3   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY JUVENILE (CONT.) \n" +
                "     3.  THEFT FROM DWELLING                      REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1968 s.1 \n" +
                "  \n" +
                "     4.  AGGRAVATED VEHICLE TAKING - TAKING       REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                DRIVING LICENCE ENDORSED \n" +
                "         THEFT ACT 1968 s.12(a)                   DISQUALIFICATION FROM \n" +
                "                                                  DRIVING 12 MTHS \n" +
                "  \n" +
                "     5.  USING VEHICLE WHILE UNINSURED            REFERRAL ORDER 12 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                DRIVING LICENCE ENDORSED \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2) \n" +
                "  \n" +
                "     6.  DRIVING OTHERWISE THAN IN ACCORDANCE     REFERRAL ORDER 12 MTHS \n" +
                "         WITH A LICENCE                           DRIVING LICENCE ENDORSED \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         ROAD TRAFFIC ACT 1988 s.87(1) \n" +
                "  \n" +
                "     7.  FAILING TO SURRENDER TO CUSTODY AT       REFERRAL ORDER 12 MTHS \n" +
                "         APPOINTED TIME \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         BAIL ACT 1976 s.6(1) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  4.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  DRIVING WHILST DISQUALIFIED              SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        CURFEW ORDER 3 MTHS WITH \n" +
                "                                                  ELECTRONIC TAGGING 6.7.05 TO \n" +
                "                                                  6.10.05 BET 9PM AND 7AM EACH \n" +
                "                                                  DAY \n" +
                "                                                  DRIVING LICENCE ENDORSED \n" +
                "  \n" +
                "     2.  USING VEHICLE WHILE UNINSURED            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT. \n" +
                "                                                  DRIVING LICENCE ENDORSED 8 \n" +
                "                                                  POINTS \n" +
                "  \n" +
                "     3.  BREACH OF REFERRAL ORDER                 ORDER REVOKED 23/12/18 \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         YOUTH JUSTICE AND CRIMINAL EVIDENCE \n" +
                "         ACT 1999 s.13 \n" +
                "  \n" +
                "     4.  TAKING MOTOR VEHICLE WITHOUT CONSENT     SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.12(1)                   CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 4   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY JUVENILE (CONT.) \n" +
                "     5.  MAKING OFF WITHOUT PAYING                SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1978 s.3                       CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     6.  DESTROY OR DAMAGE PROPERTY (VALUE OF     SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   OFFENDERS) 18 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           CONCURRENT \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW ORDER 3 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     7.  TAKING MOTOR VEHICLE WITHOUT CONSENT     SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.12(1)                   CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     8.  BURGLARY AND THEFT - DWELLING            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.9(1)(b)                 CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     9.  USING VEHICLE WHILE UNINSURED            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     10. DRIVING OTHERWISE THAN IN ACCORDANCE     SUPERVISION ORDER (YOUNG \n" +
                "         WITH A LICENCE                           OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CONCURRENT \n" +
                "         ROAD TRAFFIC ACT 1988 s.87(1)            CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     11. DESTROY OR DAMAGE PROPERTY (VALUE OF     SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   OFFENDERS) 18 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           CONCURRENT \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW ORDER 3 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 5   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY JUVENILE (CONT.) \n" +
                "     12. DESTROY OR DAMAGE PROPERTY (VALUE OF     SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   OFFENDERS) 18 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           CONCURRENT \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW ORDER 3 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     13. THEFT FROM DWELLING                      SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.1                       CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     14. AGGRAVATED VEHICLE TAKING - TAKING       SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.12(a)                   CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     15. USING VEHICLE WHILE UNINSURED            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           CONCURRENT \n" +
                "                                                  CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     16. DRIVING OTHERWISE THAN IN ACCORDANCE     SUPERVISION ORDER (YOUNG \n" +
                "         WITH A LICENCE                           OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CONCURRENT \n" +
                "         ROAD TRAFFIC ACT 1988 s.87(1)            CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     17. FAILING TO SURRENDER TO CUSTODY AT       SUPERVISION ORDER (YOUNG \n" +
                "         APPOINTED TIME                           OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CONCURRENT \n" +
                "         BAIL ACT 1976 s.6(1)                     CURFEW ORDER 3 MTHS \n" +
                "                                                  CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  5.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  BURGLARY WITH INTENT TO CAUSE UNLAWFUL   SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE - IN DWELLING                     OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW ORDER 6 MTHS WITH \n" +
                "         THEFT ACT 1968 s.9(1)(a)                 ELECTRONIC TAGGING 1.8.05 TO \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 6   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY JUVENILE (CONT.) \n" +
                "         BURGLARY WITH INTENT TO CAUSE UNLAWFUL+ (CONT.) \n" +
                "  \n" +
                "                                                  31.1.06. DAILY BET 9PM AND \n" +
                "                                                  7AM \n" +
                "  \n" +
                "     2.  BREACH OF SUPERVISION ORDER              ORDER REVOKED 23/12/18 \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CHILDREN AND YOUNG PERSONS ACT 1969 \n" +
                "         s.15(2)(a) \n" +
                "  \n" +
                "     3.  DRIVING WHILST DISQUALIFIED              SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     4.  USING VEHICLE WHILE UNINSURED            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     5.  TAKING MOTOR VEHICLE WITHOUT CONSENT     SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.12(1)                   CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     6.  MAKING OFF WITHOUT PAYING                SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1978 s.3                       CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     7.  DESTROY OR DAMAGE PROPERTY (VALUE OF     SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   OFFENDERS) 18 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           CONCURRENT \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                RESULTING FROM ORIGINAL \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     8.  TAKING MOTOR VEHICLE WITHOUT CONSENT     SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.12(1)                   CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     9.  BURGLARY AND THEFT - DWELLING            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.9(1)(b)                 CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     10. USING VEHICLE WHILE UNINSURED            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           CONCURRENT \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 7   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY JUVENILE (CONT.) \n" +
                "         USING VEHICLE WHILE UNINSURED (CONT.) \n" +
                "  \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     11. DRIVING OTHERWISE THAN IN ACCORDANCE     SUPERVISION ORDER (YOUNG \n" +
                "         WITH A LICENCE                           OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CONCURRENT \n" +
                "         ROAD TRAFFIC ACT 1988 s.87(1)            RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     12. DESTROY OR DAMAGE PROPERTY (VALUE OF     SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   OFFENDERS) 18 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           CONCURRENT \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                RESULTING FROM ORIGINAL \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     13. DESTROY OR DAMAGE PROPERTY (VALUE OF     SUPERVISION ORDER (YOUNG \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   OFFENDERS) 18 MTHS \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           CONCURRENT \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                RESULTING FROM ORIGINAL \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     14. THEFT FROM DWELLING                      SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.1                       CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     15. AGGRAVATED VEHICLE TAKING - TAKING       SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         THEFT ACT 1968 s.12(a)                   CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     16. USING VEHICLE WHILE UNINSURED            SUPERVISION ORDER (YOUNG \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OFFENDERS) 18 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           CONCURRENT \n" +
                "                                                  RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     17. DRIVING OTHERWISE THAN IN ACCORDANCE     SUPERVISION ORDER (YOUNG \n" +
                "         WITH A LICENCE                           OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CONCURRENT \n" +
                "         ROAD TRAFFIC ACT 1988 s.87(1)            RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "     18. FAILING TO SURRENDER TO CUSTODY AT       SUPERVISION ORDER (YOUNG \n" +
                "         APPOINTED TIME                           OFFENDERS) 18 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CONCURRENT \n" +
                "         BAIL ACT 1976 s.6(1)                     RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 8   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY JUVENILE (CONT.) \n" +
                "     19. BREACH OF CURFEW ORDER                   ORDER REVOKED 23/12/18 \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL JUSTICE ACT 1991 s.14 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  6.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  BREACH OF CURFEW ORDER                   ORDER REVOKED 23/12/18 \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL JUSTICE ACT 1991 s.14 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  7.    23/12/18         BADBOY JUVENILE \n" +
                "  \n" +
                "     1.  DESTROY OR DAMAGE PROPERTY (VALUE OF     NO SEPARATE PENALTY \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         (1 TIC'S) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "     2.  DRIVING WHILST DISQUALIFIED              DETENTION AND TRAINING ORDER \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     4 MTHS CONCURRENT \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        DRIVING LICENCE ENDORSED \n" +
                "  \n" +
                "     3.  MAKING OFF WITHOUT PAYING                DETENTION AND TRAINING ORDER \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                4 MTHS CONCURRENT \n" +
                "         THEFT ACT 1978 s.3 \n" +
                "  \n" +
                "     4.  MAKING OFF WITHOUT PAYING                DETENTION AND TRAINING ORDER \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                4 MTHS CONCURRENT \n" +
                "         THEFT ACT 1978 s.3 \n" +
                "  \n" +
                "     5.  AGGRAVATED VEHICLE TAKING (TAKING)       DETENTION AND TRAINING ORDER \n" +
                "         ACCIDENT CAUSE DAMAGE TO PROPERTY        4 MTHS CONCURRENT \n" +
                "         OTHER THAN VEHICLE UNDER 5000 GBP        DISQUALIFICATION FROM \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     DRIVING 12 MTHS \n" +
                "         THEFT ACT 1968 s.12A                     DRIVING LICENCE ENDORSED \n" +
                "  \n" +
                "     6.  BURGLARY AND THEFT - NON-DWELLING        DETENTION AND TRAINING ORDER \n" +
                "         (3 TIC'S)                                4 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1968 s.9(1)(b) \n" +
                "  \n" +
                "     7.  USING VEHICLE WHILE UNINSURED            NO SEPARATE PENALTY \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     DRIVING LICENCE ENDORSED \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  8.    23/12/18         BADBOY MAGISTRATES \n" +
                "  \n" +
                "     1.  DRIVING WHILST DISQUALIFIED              DETENTION AND TRAINING ORDER \n" +
                "         ON 23/12/18 - 23/12/18 (NO PLEA TAKEN)   4 MTHS CONCURRENT TO \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        EXISTING ORDER IMPOSED \n" +
                "                                                  23/12/18 \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 9   OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BADBOY MAGISTRATES (CONT.) \n" +
                "         DRIVING WHILST DISQUALIFIED (CONT.) \n" +
                "  \n" +
                "                                                  DRIVING LICENCE ENDORSED \n" +
                "                                                  DISQUALIFICATION FROM \n" +
                "                                                  DRIVING 6 MTHS \n" +
                "  \n" +
                "     2.  AGGRAVATED VEHICLE TAKING (TAKING)       DETENTION AND TRAINING ORDER \n" +
                "         ACCIDENT CAUSE DAMAGE TO VEHICLE UNDER   4 MTHS CONCURRENT \n" +
                "         5000 GBP \n" +
                "         ON 23/12/18 - 23/12/18 (NO PLEA TAKEN) \n" +
                "         THEFT ACT 1968 s.12A \n" +
                "  \n" +
                "     3.  INTERFERING WITH VEHICLE (3 TIC'S)       NO SEPARATE PENALTY \n" +
                "         ON 23/12/18 - 23/12/18 (NO PLEA TAKEN) \n" +
                "         CRIMINAL ATTEMPTS ACT 1981 s.9 \n" +
                "  \n" +
                "     4.  DESTROY OR DAMAGE PROPERTY (VALUE OF     NO SEPARATE PENALTY \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 - 23/12/18 (NO PLEA TAKEN) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "     5.  USING VEHICLE WHILE UNINSURED            NO SEPARATE PENALTY \n" +
                "         ON 23/12/18 - 23/12/18 (NO PLEA TAKEN)   DRIVING LICENCE ENDORSED \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  9.    23/12/18         LIVERPOOL JUVENILE \n" +
                "  \n" +
                "     1.  DRIVE VEHICLE TAKEN WITHOUT CONSENT      COMMUNITY REHABILITATION \n" +
                "         DANGEROUSLY                              ORDER 12 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW ORDER 3 MTHS WITH \n" +
                "         THEFT ACT 1968 s.12A(1)+s.(4)            ELECTRONIC TAGGING FROM 7PM \n" +
                "                                                  - 7AM FROM 2.5.06 - 2.8.06 \n" +
                "                                                  WITH ISSP FOR 6 MONTHS \n" +
                "                                                  DISQUALIFICATION FROM \n" +
                "                                                  DRIVING 18 MTHS AND LICENCE \n" +
                "                                                  ENDORSED RESIT TEST \n" +
                "                                                  DISQUALIFIED FROM DRIVING - \n" +
                "                                                  UNTIL EXTENDED TEST PASSED \n" +
                "                                                  COSTS 50.00 \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   LIVERPOOL JUVENILE \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER REVOKED 23/12/18 - \n" +
                "                                                  DEFENDANT DISCHARGED FROM \n" +
                "                                                  CRO & ISSP COMPLYING WITH \n" +
                "                                                  ORDER \n" +
                "  \n" +
                "     2.  DRIVING A MOTOR VEHICLE WITH EXCESS      COMMUNITY REHABILITATION \n" +
                "         ALCOHOL                                  ORDER 12 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW ORDER 3 MTHS WITH \n" +
                "         ROAD TRAFFIC ACT 1988 s.5(1)(a)          ELECTRONIC TAGGING FROM 7PM \n" +
                "                                                  -7AM FROM 2.5.06 - 2.8.06 \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 10  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         LIVERPOOL JUVENILE (CONT.) \n" +
                "         DRIVING A MOTOR VEHICLE WITH EXCESS+ (CONT.) \n" +
                "  \n" +
                "                                                  WITH ISSP FOR 6 MONTHS \n" +
                "                                                  DISQUALIFICATION FROM \n" +
                "                                                  DRIVING 18 MTHS AND LICENCE \n" +
                "                                                  ENDORSED \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   LIVERPOOL JUVENILE \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER REVOKED 23/12/18 - \n" +
                "                                                  DEFENDANT COMPLYING WELL \n" +
                "                                                  WITH ORDER \n" +
                "  \n" +
                "     3.  NO INSURANCE                             NO SEPARATE PENALTY \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                DRIVING LICENCE ENDORSED \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2) \n" +
                "  \n" +
                "     4.  DRIVING WHILST DISQUALIFIED              COMMUNITY REHABILITATION \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                ORDER 12 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        CURFEW ORDER 3 MTHS WITH \n" +
                "                                                  ELECTRONIC TAGGING FROM 7PM \n" +
                "                                                  - 7AM FROM 2.5.06 - 2.8.06 \n" +
                "                                                  WITH ISSP FOR 6 MONTHS \n" +
                "                                                  DRIVING LICENCE ENDORSED \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   LIVERPOOL JUVENILE \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER REVOKED 23/12/18 - \n" +
                "                                                  DEFENDANT COMPLYING WELL \n" +
                "                                                  WITH ORDER \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  10.   23/12/18         LIVERPOOL JUVENILE \n" +
                "  \n" +
                "     1.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REPARATION ORDER 24 HRS TO \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   MAKE REPARATION TO \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           PERSON/COMMUNITY.COMPLETE \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                WITHIN 3 MONTHS. \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  11.   23/12/18         EAST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  BEING CARRIED IN MOTOR VEHICLE TAKEN     SUSPENDED IMPRISONMENT 30 \n" +
                "         WITHOUT CONSENT                          DAYS CONSECUTIVE SUSPENDED \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     FOR 12MTHS - TOTAL 90 DAYS \n" +
                "         THEFT ACT 1968 s.12(1) \n" +
                "     **  OFFENCE COMMITTED ON BAIL  ** \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 11  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  12.   23/12/18         EAST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  DESTROY OR DAMAGE PROPERTY (VALUE OF     IMPRISONMENT 60 DAYS WHOLLY \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   SUSPENDED 12 MTHS WITH \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           SUPERVISION REQUIREMENT & TO \n" +
                "         ON 23/12/18 (PLEA:NOT KNOWN)             ATTEND THINK FIRST PORGRAMME \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          COMPENSATION 150.00 \n" +
                "  \n" +
                "     2.  USE DISORDERLY BEHAVIOUR OR              IMPRISONMENT 60 DAYS WHOLLY \n" +
                "         THREATENING/ABUSIVE/INSULTING WORDS      SUSPENDED 12 MTHS WITH \n" +
                "         LIKELY TO CAUSE HARASSMENT ALARM OR      SUPERVISION REQUIRMENT & TO \n" +
                "         DISTRESS                                 ATTEND THINK FIRST PROGRAMME \n" +
                "         ON 23/12/18 (PLEA:NOT KNOWN) \n" +
                "         PUBLIC ORDER ACT 1986 s.5(1)(a) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  13.   23/12/18         EAST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  THEFT (FROM MOTOR VEHICLE)               COMMUNITY ORDER CONCURRENT \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         THEFT ACT 1968 s.1(1) \n" +
                "  \n" +
                "     2.  DESTROY OR DAMAGE PROPERTY (VALUE OF     COMMUNITY ORDER 5MTHS \n" +
                "         DAMAGE #5000 OR LESS - OFFENCE AGAINST   CURFEW ORDER 4 MTHS WITH \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           ELECTRONIC TAGGING 18/5/07 \n" +
                "         (1 TIC'S)                                TO 17/9/07 19:00 TO 07:00 \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     DAILY \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "     3.  INTERFERING WITH VEHICLE (1 TIC'S)       COMMUNITY ORDER CONCURRENT \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL ATTEMPTS ACT 1981 s.9 \n" +
                "  \n" +
                "     4.  INTERFERING WITH VEHICLE                 COMMUNITY ORDER CONCURRENT \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL ATTEMPTS ACT 1981 s.9 \n" +
                "  \n" +
                "     5.  ATTEMPT/INTERFERING WITH VEHICLE         COMMUNITY ORDER CONCURRENT \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL ATTEMPTS ACT 1981 s.9 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  14.   23/12/18         EAST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  BREACH OF SUSPENDED SENTENCE             SUSPENDED IMPRISONMENT 90 \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     DAYS \n" +
                "         POWERS OF CRIMINAL COURTS ACT 1973       RESULTING FROM ORIGINAL \n" +
                "         s.23                                     CONVICTION OF 23/12/18 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  15.   23/12/18         WEST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  INTERFERING WITH VEHICLE                 CONDITIONAL DISCHARGE 12 \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     MTHS \n" +
                "         CRIMINAL ATTEMPTS ACT 1981 s.9           COMPENSATION 100.00 \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 12  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  16.   23/12/18         EAST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  BREACH OF SUSPENDED SENTENCE             FINE 30.00 \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                COMPENSATION 15.00 VICTIM \n" +
                "         POWERS OF CRIMINAL COURTS ACT 1973       SURCHARGE \n" +
                "         s.23 \n" +
                "  \n" +
                "     2.  BREACH OF SUSPENDED SENTENCE             FINE 30.00 \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         POWERS OF CRIMINAL COURTS ACT 1973 \n" +
                "         s.23 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  17.   23/12/18         EAST SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  BREACH OF YOI SUPERVISION ORDER          YOUNG OFFENDERS INSTITUTION \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     28 DAYS \n" +
                "         CRIMINAL JUSTICE ACT 1991 s.65 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  18.   23/12/18         BIRMINGHAM CROWN \n" +
                "  \n" +
                "     1.  AGGRAVATED VEHICLE TAKING (TAKING)       COMMUNITY ORDER 3 YRS \n" +
                "         DRIVING DANGEROUSLY ON ROAD OR PLACE     CURFEW REQUIREMENT 6 MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                WITH ELECTRONIC TAGGING 7PM \n" +
                "         THEFT ACT 1968 s.12A                     TO 7AM DAILY \n" +
                "                                                  PROGRAMME REQUIREMENT \n" +
                "                                                  THINKING SKILLS PROGRAMME \n" +
                "                                                  SUPERVISION REQUIREMENT \n" +
                "                                                  UNPAID WORK REQUIREMENT 200 \n" +
                "                                                  HRS \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED REQUIREMENT \n" +
                "                                                  ADDED FOR BREACH \n" +
                "                                                  CURFEW REQUIREMENT 28 DAYS \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED TO MARK THE \n" +
                "                                                  BREACH \n" +
                "                                                  UNPAID WORK REQUIREMENT \n" +
                "                                                  ORDER REVOKED 23/12/18 \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      IMPRISONMENT 4 MTHS \n" +
                "  \n" +
                "     2.  DRIVING WHILST DISQUALIFIED              COMMUNITY ORDER 3 YRS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                CURFEW REQUIREMENT 6 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        WITH ELECTRONIC TAGGING 7PM \n" +
                "                                                  TO 7AM DAILY \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 13  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BIRMINGHAM CROWN (CONT.) \n" +
                "         DRIVING WHILST DISQUALIFIED (CONT.) \n" +
                "  \n" +
                "                                                  PROGRAMME REQUIREMENT \n" +
                "                                                  THINKING SKILLS PROGRAMME \n" +
                "                                                  SUPERVISION REQUIREMENT \n" +
                "                                                  UNPAID WORK REQUIREMENT 200 \n" +
                "                                                  HRS \n" +
                "                                                  DISQUALIFIED FROM DRIVING - \n" +
                "                                                  OBLIGATORY 3 YRS AND TAKE \n" +
                "                                                  EXTENDED RE TEST \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED REQUIREMENT \n" +
                "                                                  ADDED FOR BREACH \n" +
                "                                                  CURFEW REQUIREMENT 28 DAYS \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED TO MARK THE \n" +
                "                                                  BREACH \n" +
                "                                                  UNPAID WORK REQUIREMENT \n" +
                "                                                  ORDER REVOKED 23/12/18 \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      IMPRISONMENT 4 MTHS \n" +
                "  \n" +
                "     3.  USING VEHICLE WHILE UNINSURED            NO SEPARATE PENALTY \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                DRIVING LICENCE ENDORSED \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2) \n" +
                "  \n" +
                "     4.  FAILING TO PROVIDE A SPECIMEN FOR        NO SEPARATE PENALTY \n" +
                "         ANALYSIS (DRIVING OR ATTEMPTING TO       DRIVING LICENCE ENDORSED \n" +
                "         DRIVE) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         ROAD TRAFFIC ACT 1988 s.7(6) \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  19.   23/12/18         BIRMINGHAM CROWN \n" +
                "  \n" +
                "     1.  FAIL TO COMPLY WITH THE REQUIREMENTS     RESULTING FROM ORIGINAL \n" +
                "         OF A COMMUNITY ORDER                     CONVICTION OF 23/12/18 \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     ORDER TO CONTINUE \n" +
                "         CRIMINAL JUSTICE ACT 2003 sch.8          ORDER VARIED \n" +
                "                                                  COSTS 100.00 \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 14  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  20.   23/12/18         BIRMINGHAM CROWN \n" +
                "  \n" +
                "     1.  FAIL TO COMPLY WITH THE REQUIREMENTS     RESULTING FROM ORIGINAL \n" +
                "         OF A COMMUNITY ORDER                     CONVICTION OF 23/12/18 \n" +
                "         ON 23/12/18 - 23/12/18 (PLEA:GUILTY)     ORDER VARIED \n" +
                "         CRIMINAL JUSTICE ACT 2003 sch.8 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  21.   23/12/18         SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REMAND ON UNCONDITIONAL BAIL \n" +
                "         DAMAGE OVER #5000 - OFFENCE AGAINST      23/12/18 \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                IMPRISONMENT 4 MTHS \n" +
                "                                                  CONSECUTIVE \n" +
                "                                                  VICTIM SURCHARGE 80.00 \n" +
                "  \n" +
                "     2.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REMAND ON UNCONDITIONAL BAIL \n" +
                "         DAMAGE OVER #5000 - OFFENCE AGAINST      23/12/18 \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "     **  OFFENCE COMMITTED ON BAIL  ** \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                IMPRISONMENT 4 MTHS \n" +
                "  \n" +
                "     3.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REMAND ON UNCONDITIONAL BAIL \n" +
                "         DAMAGE OVER #5000 - OFFENCE AGAINST      23/12/18 \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "     **  OFFENCE COMMITTED ON BAIL  ** \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                IMPRISONMENT 4 MTHS \n" +
                "  \n" +
                "     4.  DESTROY OR DAMAGE PROPERTY (VALUE OF     REMAND ON UNCONDITIONAL BAIL \n" +
                "         DAMAGE OVER #5000 - OFFENCE AGAINST      23/12/18 \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY) \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1) \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                IMPRISONMENT 4 MTHS \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 15  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         SHEFFIELD MAGISTRATES (CONT.) \n" +
                "     5.  CONVICTION FOR OFFENCE WHILST            COMMIT TO CROWN COURT FOR \n" +
                "         COMMUNITY ORDER IS IN FORCE              SENTENCE - UNCONDITIONAL \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                BAIL 23/12/18 \n" +
                "         CRIMINAL JUSTICE ACT 2003 sch.8+pt.5 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "                                                  ORDER REVOKED 23/12/18 \n" +
                "                                                  RESENTENCED FOR THE ORIGINAL \n" +
                "                                                  OFFENCE \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  22.   23/12/18         SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  FAILING TO PROVIDE A SPECIMEN FOR        FINE 1000.00 \n" +
                "         ANALYSIS (DRIVING OR ATTEMPTING TO       COSTS 250.00 \n" +
                "         DRIVE)                                   DISQUALIFIED FROM DRIVING - \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                OBLIGATORY 52 MTHS \n" +
                "         ROAD TRAFFIC ACT 1988 s.7(6)             VICTIM SURCHARGE 100.00 \n" +
                "                                                  CRIMINAL COURTS CHARGE \n" +
                "                                                  150.00 \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  23.   23/12/18         BIRMINGHAM CROWN \n" +
                "  \n" +
                "     1.  THREATS TO KILL                          SUSPENDED IMPRISONMENT 24 \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                MTHS WHOLLY SUSPENDED 24 \n" +
                "         OFFENCES AGAINST THE PERSON ACT 1861     MTHS \n" +
                "         s.16                                     SUPERVISION REQUIREMENT \n" +
                "                                                  REHABILITATION ACTIVITY \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED ADDITIONAL \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  UNPAID WORK REQUIREMENT 20 \n" +
                "                                                  HRS \n" +
                "  \n" +
                "     2.  DESTROY OR DAMAGE PROPERTY (VALUE OF     SUSPENDED IMPRISONMENT 6 \n" +
                "         DAMAGE OVER #5000 - OFFENCE AGAINST      MTHS WHOLLY SUSPENDED 24 \n" +
                "         CRIMINAL DAMAGE ACT 1971 ONLY)           MTHS \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                SUPERVISION REQUIREMENT \n" +
                "         CRIMINAL DAMAGE ACT 1971 s.1(1)          REHABILITATION ACTIVITY \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  VICTIM SURCHARGE 115.00 \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED ADDITIONAL \n" +
                "                                                  REQUIREMENT \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 16  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         BIRMINGHAM CROWN (CONT.) \n" +
                "         DESTROY OR DAMAGE PROPERTY (VALUE OF+ (CONT.) \n" +
                "         23/12/18   BIRMINGHAM CROWN (CONT.) \n" +
                "                                                  UNPAID WORK REQUIREMENT 20 \n" +
                "                                                  HRS \n" +
                "  \n" +
                "     3.  ASSAULT OCCASIONING ACTUAL BODILY HARM   SUSPENDED IMPRISONMENT 8 \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                MTHS WHOLLY SUSPENDED 24 \n" +
                "         OFFENCES AGAINST THE PERSON ACT 1861     MTHS \n" +
                "         s.47                                     SUPERVISION REQUIREMENT \n" +
                "                                                  REHABILITATION ACTIVITY \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED ADDITIONAL \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  UNPAID WORK REQUIREMENT 20 \n" +
                "                                                  HRS \n" +
                "  \n" +
                "     4.  AFFRAY                                   SUSPENDED IMPRISONMENT 24 \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                MTHS WHOLLY SUSPENDED 24 \n" +
                "         PUBLIC ORDER ACT 1986 s.3                MTHS \n" +
                "                                                  SUPERVISION REQUIREMENT \n" +
                "                                                  REHABILITATION ACTIVITY \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  SUBSEQUENTLY VARIED 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SUBSEQUENTLY VARIED                      ORDER VARIED ADDITIONAL \n" +
                "                                                  REQUIREMENT \n" +
                "                                                  UNPAID WORK REQUIREMENT 20 \n" +
                "                                                  HRS \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  \n" +
                "  24.   23/12/18         SHEFFIELD MAGISTRATES \n" +
                "  \n" +
                "     1.  DRIVING WHILST DISQUALIFIED              COMMIT TO CROWN COURT FOR \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                SENTENCE - UNCONDITIONAL \n" +
                "         ROAD TRAFFIC ACT 1988 s.103(1)(b)        BAIL 23/12/18 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                COMMUNITY ORDER 23/12/18 \n" +
                "                                                  UNPAID WORK REQUIREMENT 100 \n" +
                "                                                  HRS CONSECUTIVE \n" +
                "                                                  DISQUALIFIED FROM DRIVING - \n" +
                "                                                  OBLIGATORY 12 MTHS \n" +
                "  \n" +
                "     2.  USING VEHICLE WHILE UNINSURED            COMMIT TO CROWN COURT FOR \n" +
                "         ON 23/12/18 (PLEA:GUILTY)                SENTENCE - UNCONDITIONAL \n" +
                "         ROAD TRAFFIC ACT 1988 s.143(2)           BAIL 23/12/18 \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "                                                         CONTINUED ON NEXT PAGE \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 17  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "        23/12/18         SHEFFIELD MAGISTRATES (CONT.) \n" +
                "         USING VEHICLE WHILE UNINSURED (CONT.) \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                NO SEPARATE PENALTY \n" +
                "                                                  DRIVING LICENCE ENDORSED \n" +
                "  \n" +
                "     3.  COMMISSION OF FURTHER OFFENCE DURING     COMMIT TO CROWN COURT FOR \n" +
                "         OPERATIONAL PERIOD OF SUSPENDED          SENTENCE - UNCONDITIONAL \n" +
                "         SENTENCE ORDER                           BAIL 23/12/18 \n" +
                "         ON 23/12/18 (PLEA:GUILTY) \n" +
                "         CRIMINAL JUSTICE ACT 2003 sch.12 \n" +
                "  \n" +
                "         23/12/18   BIRMINGHAM CROWN \n" +
                "  \n" +
                "         SENTENCED                                RESULTING FROM ORIGINAL \n" +
                "                                                  CONVICTION OF 23/12/18 \n" +
                "                                                  ORDER VARIED \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  END OF CONVICTION REPORTS \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "   \n" +
                "   \n" +
                "    23/12/18 12:14                                          PAGE 18  OF 18 \n" +
                "  NAME: DEFENDANT, JOHNNY                                         PNCID: 18/123456Z \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "                            REMAND / BAIL DETAILS \n" +
                "                            --------------------- \n" +
                "  \n" +
                "  ARREST/SUMMONS REF : 18/0023/12/1865190V \n" +
                "        23/12/18             DEVON AND SHEFFIELD \n" +
                "      UNDER INVESTIGATION \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  ARREST/SUMMONS REF : 18/0023/12/1803396P \n" +
                "        23/12/18             DEVON AND SHEFFIELD \n" +
                "      REMANDED ON BAIL \n" +
                "      NEXT APPEARING ON 23/12/18 AT SHEFFIELD MAGISTRATES \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  END OF REMAND DETAILS \n" +
                "  \n" +
                "  \n" +
                "                           LAST PERIOD IN CUSTODY \n" +
                "                           ---------------------- \n" +
                "  \n" +
                "     PRISONER NUMBER      :  GA5228 \n" +
                "     LOCATION             :  BLIGHTY \n" +
                "     ACTUAL RELEASE DATE  :  23/12/18 \n" +
                "     SENTENCE EXPIRY DATE :  23/12/18 \n" +
                "     REASON FOR RELEASE   :  CONDITIONAL RELEASE \n" +
                "     LICENCE TYPE         :  AT RISK NOTICE \n" +
                "     LICENCE CONDITIONS   : \n" +
                "        RELEASED UNDER THE PROVISONS OF S.76 C.J. ACT 1998 \n" +
                "  \n" +
                "  \n" +
                "  \n" +
                "  ----------------------------------------------------------------------------- \n" +
                "  END OF CUSTODY DETAILS \n" +
                "  \n" +
                "  END OF PNC RECORD PRINT \n" +
                "\n";
    }
}
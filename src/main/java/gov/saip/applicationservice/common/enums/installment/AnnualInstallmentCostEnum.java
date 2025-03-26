 package gov.saip.applicationservice.common.enums.installment;

 import lombok.AllArgsConstructor;
 import lombok.Getter;

 import java.util.Arrays;
 import java.util.Optional;

 @Getter
 @AllArgsConstructor
 public enum AnnualInstallmentCostEnum {
//     ANNUAL_FEES_YEAR_25(25, null),
//     ANNUAL_FEES_YEAR_24(24, ANNUAL_FEES_YEAR_25),
//     ANNUAL_FEES_YEAR_23(23, ANNUAL_FEES_YEAR_24),
//     ANNUAL_FEES_YEAR_22(22, ANNUAL_FEES_YEAR_23),
//     ANNUAL_FEES_YEAR_21(21, ANNUAL_FEES_YEAR_22),
     ANNUAL_FEES_YEAR_20(20, null),
     ANNUAL_FEES_YEAR_19(19, ANNUAL_FEES_YEAR_20),
     ANNUAL_FEES_YEAR_18(18, ANNUAL_FEES_YEAR_19),
     ANNUAL_FEES_YEAR_17(17, ANNUAL_FEES_YEAR_18),
     ANNUAL_FEES_YEAR_16(16, ANNUAL_FEES_YEAR_17),
     ANNUAL_FEES_YEAR_15(15, ANNUAL_FEES_YEAR_16),
     ANNUAL_FEES_YEAR_14(14, ANNUAL_FEES_YEAR_15),
     ANNUAL_FEES_YEAR_13(13, ANNUAL_FEES_YEAR_14),
     ANNUAL_FEES_YEAR_12(12, ANNUAL_FEES_YEAR_13),
     ANNUAL_FEES_YEAR_11(11, ANNUAL_FEES_YEAR_12),
     ANNUAL_FEES_YEAR_10(10, ANNUAL_FEES_YEAR_11),
     ANNUAL_FEES_YEAR_09(9,  ANNUAL_FEES_YEAR_10),
     ANNUAL_FEES_YEAR_08(8,  ANNUAL_FEES_YEAR_09),
     ANNUAL_FEES_YEAR_07(7,  ANNUAL_FEES_YEAR_08),
     ANNUAL_FEES_YEAR_06(6,  ANNUAL_FEES_YEAR_07),
     ANNUAL_FEES_YEAR_05(5,  ANNUAL_FEES_YEAR_06),
     ANNUAL_FEES_YEAR_04(4,  ANNUAL_FEES_YEAR_05),
     ANNUAL_FEES_YEAR_03(3,  ANNUAL_FEES_YEAR_04),
     ANNUAL_FEES_YEAR_02(2,  ANNUAL_FEES_YEAR_03),
     ANNUAL_FEES_YEAR_01(1,  ANNUAL_FEES_YEAR_02)
     ;

     private int index;
     private AnnualInstallmentCostEnum nextYear;

     public static AnnualInstallmentCostEnum getYearByIndex(int id) {
         Optional<AnnualInstallmentCostEnum> yearOpt = Arrays.stream(AnnualInstallmentCostEnum.values()).filter(year -> year.getIndex() == id).findFirst();
         return yearOpt.orElse(null);
     }

 }

package gov.saip.applicationservice.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import gov.saip.applicationservice.common.dto.DurationAndPercentageDto;
import gov.saip.applicationservice.common.dto.DurationDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.DateTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.HijrahChronology;
import java.time.chrono.HijrahDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gov.saip.applicationservice.common.enums.ApplicationRelevantEnum.Applicant_MAIN;
import static gov.saip.applicationservice.common.enums.DateTypeEnum.HIJRI;
import static gov.saip.applicationservice.util.Constants.AppRequestHeaders.CUSTOMER_CODE;
import static gov.saip.applicationservice.util.Constants.AppRequestHeaders.CUSTOMER_ID;
import static gov.saip.applicationservice.util.Constants.ErrorKeys.DATE_FORMAT_EXCEPTION;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class Utilities {

    public static String filerServiceURL;
    
    public static final DateTimeFormatter yyyy_MM_dd_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    public static String getFilerServiceURL() {
        return filerServiceURL;
    }

    public static String getLastTwoDigitOfYear(LocalDateTime localDateTime) {
        return String.valueOf(localDateTime.getYear() - 2000);
    }

    public static String getLastTwoDigitOfYearHijri(LocalDateTime localDateTime) {
        return String.valueOf(convertLocalDateToHijri(localDateTime).get(ChronoField.YEAR) - 1400);
    }

    public static void main(String[] args) {
        Long number = 7L;
        xx(LocalDateTime.now());
        System.out.println(getPadded5Number(number));
        System.out.println(getLastTwoDigitOfYear(LocalDateTime.now()));
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = LocalDateTime.now().minus(28l, ChronoUnit.HOURS);
        DurationDto durationDto = calculateDuration(to, from);
        System.out.println(durationDto);
    }

    public static String getPadded5Number(Long number) {
        return String.format("%05d", number);
    }
    public static String getPadded4Number(Long number) {
        return String.format("%04d", number);
    }

    public static String getLastTwoDigitOfYear(Long n) {
        return String.valueOf(LocalDateTime.now().getYear() - 2000);
    }

    public static String xx(LocalDateTime localDateTime) {

        LocalDateTime firstDay = localDateTime.with(firstDayOfYear()); // 2015-01-01
        LocalDateTime lastDay = localDateTime.with(lastDayOfYear()); // 2015-12-31
        System.out.println(firstDay);
        System.out.println(lastDay);
        return String.valueOf(LocalDateTime.now().getYear() - 2000);
    }

    public static LocalDateTime getFirstSecondOfYear(LocalDateTime dateTime) {
        return dateTime.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    public static LocalDateTime getLastSecondOfYear(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
    }

    public static LocalDateTime getFirstSecondOfDay(LocalDateTime dateTime) {
        return (dateTime != null) ?  dateTime.withHour(0).withMinute(0).withSecond(0).withNano(0) :  null;
    }

    public static LocalDateTime getLastSecondOfDay(LocalDateTime dateTime) {
        return (dateTime != null) ? dateTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999) : null;
    }

    public static DurationDto calculateDuration(LocalDateTime from, LocalDateTime to) {
        Duration duration = Duration.between(from, to);
        long totalHours = duration.toHours();
        long days = totalHours / 24;
        long hours = totalHours % 24;

        return new DurationDto(days < 0 ? 0L : days, hours < 0 ? 0L : hours , 0L);
    }

    public static DurationDto calculateRemainingDuration(LocalDateTime from, LocalDateTime to) {
        // Calculate the duration between now and the expiration date
        Duration remainingDuration = Duration.between(from, to);

        // Get the total number of minutes in the remaining duration
        long totalMinutes = remainingDuration.toMinutes();

        // Calculate days, hours, and minutes from totalMinutes
        long days = totalMinutes / (60 * 24); // 1 day = 24 hours = 1440 minutes
        long hours = (totalMinutes % (60 * 24)) / 60;
        long minutes = totalMinutes % 60;

        // Create a DurationDto object to hold the remaining days, hours, and minutes
        return new DurationDto(days < 0 ? 0L : days, hours < 0 ? 0L : hours , minutes < 0 ? 0L : minutes);
    }

    public static String getApplicationUrl(String baseUrl, String categoryCode, Long id) {
        StringBuilder stringBuilder = new StringBuilder(baseUrl).append("kc/");
        if (categoryCode.equalsIgnoreCase("PATENT")) {
            stringBuilder.append("patents/requests-list");
            return stringBuilder.toString();
        } else if (categoryCode.equalsIgnoreCase("INDUSTRIAL_DESIGN")) {
            stringBuilder.append("designs/requests-list");
            return stringBuilder.toString();
        } else if (categoryCode.equalsIgnoreCase("TRADEMARK")) {
            stringBuilder.append("trademarks/requests-list");
            return stringBuilder.toString();
        }
        return null;
    }


    public static OffsetDateTime convertStringIntoOffsetDateTime(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return OffsetDateTime.parse(date, formatter);
    }

    public static String convertOffsetDateTimeIntoString(OffsetDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }
    
    
    public static LocalDate convertDateFromHijriToGregorian(LocalDate hijriDate) {
        if(hijriDate == null)
            return null;
        HijrahDate date = HijrahChronology.INSTANCE.
                date(hijriDate.get(ChronoField.YEAR), hijriDate.get(ChronoField.MONTH_OF_YEAR), hijriDate.get(ChronoField.DAY_OF_MONTH));
        System.out.println(date);
        return IsoChronology.INSTANCE.date(date);
    }
    
    public static String convertDateFromGregorianToHijri(LocalDate gregorianDate) {
        if (gregorianDate == null)
            return null;
        HijrahDate hijriDate = HijrahChronology.INSTANCE.date(gregorianDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return hijriDate.format(formatter);
    }
    
    public static String convertDateFromGregorianToHijriWithFormat(LocalDate gregorianDate, String format) {
        if (gregorianDate == null)
            return null;
        HijrahDate hijriDate = HijrahChronology.INSTANCE.date(gregorianDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return hijriDate.format(formatter);
    }

    public static HijrahDate convertLocalDateToHijri(LocalDateTime localDateTime) {
        if (localDateTime == null)
            return null;
        return HijrahChronology.INSTANCE.date(localDateTime);
    }
    
        public static Double calculatePercentageFromDuration(Long totalDays, Long remainingDays, Long remainingHours) {
            
            if (remainingDays < 0 || remainingDays > totalDays) {
                return null;
            }
            
            if (remainingHours < 0 || remainingHours >= 24) {
                return null;
            }
            
            if (totalDays <= 0) {
                return null;
            }
            
            // Convert totalDays to hours
            Long remainingTotalHours = remainingDays * 24 + remainingHours;
            Long totalTotalHours = totalDays * 24;
            
            // Calculate the percentage
            return (double) remainingTotalHours / totalTotalHours * 100;

    }
    
    public static void calculateDurationWithPercentage(LocalDateTime publicationDateWithDuration, Long durationValue,
                                                                           DurationAndPercentageDto publicationRemainingTime) {
        
        DurationDto duration  = Utilities.calculateDuration(LocalDateTime.now(), publicationDateWithDuration);
        Double percentage = Utilities.calculatePercentageFromDuration(durationValue, duration.getDays(), duration.getHours());
        publicationRemainingTime.setRemainingDuration(duration);
        publicationRemainingTime.setRemainingDurationPercentage(percentage);
    }


    @SneakyThrows
    public byte[] generateQRCodeImage(String barcodeText){
        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        return pngOutputStream.toByteArray();
    }


    public static String extractCustomerCode(ApplicationInfo applicationInfo) {
        return applicationInfo.getApplicationRelevantTypes()
                .stream()
                .filter(applicationRelevantType -> Applicant_MAIN.equals(applicationRelevantType.getType()))
                .map(ApplicationRelevantType::getCustomerCode)
                .findFirst()
                .orElse(null);
    }
    public static Long isLong(String str) {
        try {
            long number = Long.parseLong(str);
            return number;
        } catch (NumberFormatException e) {
            // If the string is not a valid long, it will throw a NumberFormatException
            return null;
        }
    }

    public static List<Long> isLong(List<String> strList) {
        try {
            if(strList == null)
                return null;
            List<Long> list = new ArrayList<>();
            for(String str : strList) {
                Long number = isLong(str);
                if(number != null)
                    list.add(number);
            }
            return list.isEmpty() ? null : list;
        } catch (NumberFormatException e) {
            // If the string is not a valid long, it will throw a NumberFormatException
            return null;
        }
    }

    private LocalDateTime parseDateStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static HttpServletRequest getHttpServletRequest() {
        return  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static String getCustomerCodeFromHeaders() {
        return  getHttpServletRequest().getHeader(CUSTOMER_CODE.getKey());
    }

    public static boolean isExternal() {
        // Check if running in a cron job (no request attributes available)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            // If there are no request attributes, assume it's a cron job.
            return false;
        }
        String requestURI = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRequestURI();
        return (requestURI.contains("/kc/") || requestURI.contains("/pb/")) || isInternalCallingFromExternalService();
    }

    public static String getCustomerIdFromHeaders() {
        return  getHttpServletRequest().getHeader(CUSTOMER_ID.getKey());
    }

    public static String getHeaderByName(String name) {
        return  getHttpServletRequest().getHeader(name);
    }

    public static boolean isInternalCallingFromExternalService() {
        return  Constants.SERVICE_TYPE.equals(getHeaderByName(Constants.AppRequestHeaders.MAIN_SERVICE_TYPE.getKey()));
    }

     public static Long getCustomerIdFromHeadersAsLong() {
         String customerId = getCustomerIdFromHeaders();
         return  customerId == null ? null : Long.valueOf(getCustomerIdFromHeaders());
    }

    public static String getAuthorizationFromRequestHeaders() {
        return Utilities.getHttpServletRequest().getHeader("Authorization");
    }

    public static int returnDays(String duration){
        // Extract the number of days using a regular expression
        Pattern pattern = Pattern.compile("P(\\d+)D");
        Matcher matcher = pattern.matcher(duration);

        if (matcher.matches()) {
            // Extract the number of days from the matched group
            int numberOfDays = Integer.parseInt(matcher.group(1));
            return numberOfDays;
        }

        return 0;
    }
    
    
    public static LocalDate convertHijriDateToLocalDate(DateTypeEnum dateType, Date date) {
        
        if (HIJRI.equals(dateType))
            return convertToLocalDate(date);
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    public static LocalDate convertToLocalDate(Date hijriDate) {
        try {
            if (hijriDate == null)
                return null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = sdf.format(hijriDate);
            String[] hijriDateParts = formattedDate.split("/");
            
            int year = Integer.parseInt(hijriDateParts[0]);
            int month = Integer.parseInt(hijriDateParts[1]);
            int day = Integer.parseInt(hijriDateParts[2]);
            HijrahDate hd = HijrahChronology.INSTANCE.date(year, month, day);
            return LocalDate.from(hd);
            
        } catch (Exception ex) {
            throw new BusinessException(DATE_FORMAT_EXCEPTION, HttpStatus.BAD_REQUEST);
        }
    }
    public static LocalDateTime  getLocalDateTimeAfterAddingYearsToHijri(Long amount,LocalDateTime fillingDate) {
        HijrahDate hijriDate = HijrahChronology.INSTANCE.date(fillingDate.toLocalDate());
        hijriDate= hijriDate.plus(amount,ChronoUnit.YEARS);
        LocalDate endOfProtectionDate = LocalDate.from(hijriDate);
        LocalTime localTime = fillingDate.toLocalTime();
        return LocalDateTime.of(endOfProtectionDate, localTime);
    }
    public static String replaceFileExtension(String fileName, String newExtension) {
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex != -1) {
            String baseName = fileName.substring(0, dotIndex);
            return baseName + "." + newExtension;
        }

        return fileName + "." + newExtension;
    }



    public static boolean isStartDateEqualEndDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        return startDate.equals(endDate);
    }

    public static boolean isEndDateBeforeStartDate(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return false;
        }
        return endDate.isBefore(startDate);
    }
    public static String getVariable(Map<String, Object> body, String key) {
        Map<String, String> value = (Map<String, String>) body.get(key);
        return Objects.isNull(value) ? "" : String.valueOf(value.get("value"));
    }

    public static boolean isProductionCategory(String saipCode) {
        return ApplicationCategoryEnum.TRADEMARK.name().equals(saipCode)
                ||
                ApplicationCategoryEnum.INDUSTRIAL_DESIGN.name().equals(saipCode)
                ||
                ApplicationCategoryEnum.PATENT.name().equals(saipCode);
    }

    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }



}

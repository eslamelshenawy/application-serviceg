package gov.saip.applicationservice.common.util;

import java.util.regex.Pattern;

public class PatentDescriptiveUtil {

  public  static   String removeSomeStylingFromHTML(String htmlwithstyles){
        if (htmlwithstyles == null || htmlwithstyles.isEmpty()) {
            return ""; // Handle null or empty input gracefully
        }
      // Compile regex patterns once to improve performance
      Pattern tagsPattern = Pattern.compile("(?i)</?(b|strong|i|u|s|em)>"); // Matches tags
      Pattern textDecorationPattern = Pattern.compile("(?i)text-decoration:\\s*(underline|line-through|overline|none);?");
      Pattern fontWeightPattern = Pattern.compile("(?i)font-weight:\\s*bold;?"); // Matches font-weight:bold
      Pattern fontStylePattern = Pattern.compile("(?i)font-style:\\s*(italic|oblique);?");
     // Pattern fontSizePattern = Pattern.compile("(?i)font-size:\\s*\\d+(?:px|pt|em|%)?"); // Matches font-size with any value
      Pattern emptyStylePattern = Pattern.compile("\\s*style=\"\\s*\""); // Matches empty style attributes
      Pattern fontWeightAsTextPattern = Pattern.compile("(?i)font-weight:\\s*bold"); // Matches font-weight:bold as plain text
      Pattern msoBreakPattern = Pattern.compile("(?i)mso-break-type:\\s*section-break;?"); // Matches mso-break-type:section-break
      Pattern lineHeightPattern = Pattern.compile("(?i)line-height:\\s*[^;]+;?");
      Pattern marginPattern = Pattern.compile("(<p[^>]*?)margin-bottom:\\s*\\d+pt;?" );
	  Pattern awSdtTagPattern = Pattern.compile("-aw-sdt-tag:\\s*'';?"); // Matches -aw-sdt-tag:''
      // Perform replacements
      String htmlWithoutSomeStyle = tagsPattern.matcher(htmlwithstyles).replaceAll(""); // Remove specific tags
      htmlWithoutSomeStyle = textDecorationPattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove text-decoration:line-through
      htmlWithoutSomeStyle = fontWeightPattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove font-weight:bold from styles
      htmlWithoutSomeStyle = fontStylePattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove font-style:italic from styles
     // htmlWithoutSomeStyle = fontSizePattern.matcher(htmlWithoutSomeStyle).replaceAll("font-size:14pt"); // Normalize font-size to 14pt
      htmlWithoutSomeStyle = fontWeightAsTextPattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove font-weight:bold as text
      htmlWithoutSomeStyle = emptyStylePattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove empty style attributes
      htmlWithoutSomeStyle = msoBreakPattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove mso-break-type:section-break
      htmlWithoutSomeStyle =  lineHeightPattern.matcher(htmlWithoutSomeStyle).replaceAll("line-height:100%;");
      htmlWithoutSomeStyle =  marginPattern.matcher(htmlWithoutSomeStyle).replaceAll("$1margin-bottom:0pt;");
	  htmlWithoutSomeStyle = awSdtTagPattern.matcher(htmlWithoutSomeStyle).replaceAll(""); // Remove -aw-sdt-tag:''



      return htmlWithoutSomeStyle;
    }

}

package com.barogo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

  /**
   * @param target 검사할 문자열
   * @param regex 검사할 정규표현식
   * @return true: 성공 / false: 실패 -> 실패일 시 서비스에서 throw exception
   */
  private static boolean validateRegex(String target, String regex) {
    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(target);
    return matcher.matches();
  }

  /**
   * 영어소문자, 영어대문자, 숫자 4~20자
   * @param target 회원 아이디
   * @return boolean
   */
  public static boolean isValidLoginId(String target) {
    String regex = "^[a-zA-Z0-9]{4,20}$";
    return validateRegex(target, regex);
  }

  /**
   * 영어소문자, 영어대문자, 숫자, 특수문자 최소 3가지 조합을 포함한 12~30자
   * @param target 회원 비밀번호
   * @return
   */
  public static boolean isValidPassword(String target) {
    String regexLowerUpperDigit = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{12,30}$";
    String regexLowerUpperSpecial = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{12,30}$";
    String regexLowerDigitSpecial = "^(?=.*[a-z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{12,30}$";
    String regexUpperDigitSpecial = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{12,30}$";

    return validateRegex(target, regexLowerUpperDigit)
        || validateRegex(target, regexLowerUpperSpecial)
        || validateRegex(target, regexLowerDigitSpecial)
        || validateRegex(target, regexUpperDigitSpecial);
  }

  /**
   * 문 대소문자, 공백, 작은따옴표('), 하이픈(-) 허용 최대 50자
   * @param target 외국인 회원 이름
   * @return boolean
   */
  public static boolean isValidForeignName(String target) {
    String regex = "^[a-zA-Z\\s'\\-]{2,50}$";
    return validateRegex(target, regex);
  }

  /**
   * 한글 최대 50자
   * @param target 내국인 회원 이름
   * @return boolean
   */
  public static boolean isValidKoreanName(String target) {
    String regex = "^[가-힣]{2,50}$";
    return validateRegex(target, regex);
  }

  /**
   * 숫자 10~11자
   * @param target 어드민 휴대폰 번호
   * @return boolean
   */
  public static boolean isValidPhone(String target) {
    String regex = "^[\\d]{10,11}$";
    return validateRegex(target, regex);
  }

  /*
   * @param target 위도
   * @return
   */
  public static boolean isValidLatitude(String target) {
    String regex = "^(3[3-8])(\\.\\d{1,6})?$";
    return validateRegex(target, regex);
  }

  /**
   * @param target 경도
   * @return
   */
  public static boolean isValidLongitude(String target) {
    String regex = "^(12[4-9]|13[0-1])(\\.\\d{1,6})?$";
    return validateRegex(target, regex);
  }

}

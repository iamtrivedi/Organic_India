package com.organic.india.common;

import com.organic.india.pojo.User_profile_data;
import com.organic.india.pojo.leave_category.Leave_category;
import com.organic.india.singletone.Organic_india;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Constant {

    public static class Route {

        public static final int Dashboard=0;
        public static final int New_leave_application=1;
        public static final int My_leave_applications=2;
        public static final int Mark_attendance=3;
        public static final int Attendance_report=4;
        public static final int Change_password=5;
        public static final int User_profile=6;
        public static final int Attendance_added=7;
        public static final int Attendance_request=8;
        public static final int Team_leave_request=9;
        public static final int Team_leave_request_report=10;
    }

    public static List<User_profile_data> profile_dataList (){

        List<User_profile_data> profile_dataList=new ArrayList<>();

        profile_dataList.add(new User_profile_data("Employee Name", ""+Organic_india.getInstance().getMe().getName()));
        profile_dataList.add(new User_profile_data("Employee Code",""+Organic_india.getInstance().getMe().getEmployeeCode()));
        profile_dataList.add(new User_profile_data("Date Of Joining",""+Organic_india.getInstance().getMe().getJoiningDate()));
        profile_dataList.add(new User_profile_data("Father Name",""+Organic_india.getInstance().getMe().getFatherName()));
        profile_dataList.add(new User_profile_data("Cost Center/Store Codes",""+Organic_india.getInstance().getMe().getStoreCodes()));
        profile_dataList.add(new User_profile_data("Cadre",""+Organic_india.getInstance().getMe().getCadre()));
        profile_dataList.add(new User_profile_data("Band",""+Organic_india.getInstance().getMe().getBand()));
        profile_dataList.add(new User_profile_data("Date of Birth",""+Organic_india.getInstance().getMe().getDateOfBirth()));
        profile_dataList.add(new User_profile_data("Email",""+Organic_india.getInstance().getMe().getEmail()));
        profile_dataList.add(new User_profile_data("Contact No",""+Organic_india.getInstance().getMe().getMobile()));
        profile_dataList.add(new User_profile_data("Blood group",""+Organic_india.getInstance().getMe().getBloodGroup()));
        profile_dataList.add(new User_profile_data("Gender",""+Organic_india.getInstance().getMe().getGender()));
        profile_dataList.add(new User_profile_data("Location",""+Organic_india.getInstance().getMe().getLocation()));
        profile_dataList.add(new User_profile_data("Emergency Contact Name",""+Organic_india.getInstance().getMe().getEmergencyName()));
        profile_dataList.add(new User_profile_data("Emergency Contact Mobile",""+Organic_india.getInstance().getMe().getEmergencyMobile()));
        profile_dataList.add(new User_profile_data("UAN No",""+Organic_india.getInstance().getMe().getUanNo()));
        profile_dataList.add(new User_profile_data("ESIC No",""+Organic_india.getInstance().getMe().getEsicNo()));
        profile_dataList.add(new User_profile_data("Bank Account No",""+Organic_india.getInstance().getMe().getBankAccountNo()));
        profile_dataList.add(new User_profile_data("Name of the Bank",""+Organic_india.getInstance().getMe().getBankName()));
        profile_dataList.add(new User_profile_data("Branch",""+Organic_india.getInstance().getMe().getBranch()));
        profile_dataList.add(new User_profile_data("IFSC Code",""+Organic_india.getInstance().getMe().getIfscCode()));
        profile_dataList.add(new User_profile_data("PAN Number",""+Organic_india.getInstance().getMe().getPanNo()));
        profile_dataList.add(new User_profile_data("Aadhar Number",""+Organic_india.getInstance().getMe().getAadharNo()));
        profile_dataList.add(new User_profile_data("Present Address",""+Organic_india.getInstance().getMe().getPresentAddress()));
        profile_dataList.add(new User_profile_data("Permanent Address",""+Organic_india.getInstance().getMe().getPermanentAddress()));

        return profile_dataList;
    }


    public static List<Leave_category> leave_types () {

        List<Leave_category> leave = new ArrayList<>();
        leave.add(new Leave_category("20","Half Day Leave (ML)"));
        leave.add(new Leave_category("1","Medical Leave (ML)"));
        leave.add(new Leave_category("2","Earned Leave (EL)"));
        leave.add(new Leave_category("3","Casual Leave (CL)"));
        leave.add(new Leave_category("19","Half Day Leave (CL)"));
        leave.add(new Leave_category("11","Birthday Leave (BL)"));
        leave.add(new Leave_category("12","Compensatory Off (CO)"));
        leave.add(new Leave_category("22","Half Day Compensatory Off Leave (CO) "));

        return leave;
    }

    public static String random_color (int pos) {
        List<String> color = new ArrayList<>();
        color.add("#EEF4B2");
        color.add("#EAB6F3");
        color.add("#BCF6BE");
        color.add("#F6E0C0");
        color.add("#B1F1EB");
        color.add("#D6F8AE");
        color.add("#C1C9F8");
        color.add("#C7C7E1");
        color.add("#D4DBFD");
        color.add("#A8F4DA");
        return color.get(pos);
    }

    public static String api_date(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return  sdf.format(calendar.getTime());
    }

    public static String formated_date(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return  sdf.format(calendar.getTime());
    }

    public static String yyyy_mm_dd(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(calendar.getTime());
    }


    public static String yyyy_mm(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return  sdf.format(calendar.getTime());
    }

    public static String hh_mm_ss_ampm(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.US);
        return  sdf.format(calendar.getTime());
    }


    public static String yyyy_mm_dd_hh_mm_ss_ampm(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
        return  sdf.format(calendar.getTime());
    }

    public static Calendar yyyy_mm_dd_str_to_date(String date_str){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(date_str);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}

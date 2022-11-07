package com.e.weatherappassignment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.e.weatherappassignment.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static com.e.weatherappassignment.MyApp.latitude;
import static com.e.weatherappassignment.MyApp.longitude;
import static java.lang.Float.parseFloat;

public class FrequentFunction {
    public static void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void blackStatusBarText(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorBlack));

        }
    }

    public static void skyBlueStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    public static void closeKeyboard(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static String dateDifference(String createdTime) {


        String time = "null";
        SimpleDateFormat newFormat = new SimpleDateFormat(
                "dd-MMM-yyyy h:mm:ss a");

        try {
            Calendar currentdate = Calendar.getInstance();
            DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy h:mm:ss a");
            TimeZone obj = TimeZone.getTimeZone("UTC");
            formatter.setTimeZone(obj);

            String inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
            String OutPutFormat = "dd-MMM-yyyy h:mm:ss a";
            String convertedDate = new SimpleDateFormat(OutPutFormat)
                    .format(new SimpleDateFormat(inputFormat)
                            .parse(createdTime));

            Date oldDate = newFormat.parse(convertedDate);

            String formattedDate = formatter.format(currentdate.getTime());
            Date currentDate = newFormat.parse(formattedDate);

            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            long hours = TimeUnit.MILLISECONDS.toHours(diff);
            long days = TimeUnit.MILLISECONDS.toDays(diff);

            if (hours > 24) {
                time = days + " days ago";
                return time;
            } else if (hours < 24 && hours >= 1) {
                time = hours + " hours ago";
                return time;
            } else if (minutes < 60 && seconds > 59) {
                time = minutes + " minutes ago";
                return time;
            } else {
                time = seconds + " seconds ago";
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static File compressFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToCurrentTimeZone(String Date) {
        String converted_date = "";
        try {

            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = utcFormat.parse(Date);

            DateFormat currentTFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            currentTFormat.setTimeZone(TimeZone.getTimeZone(getCurrentTimeZone()));

            converted_date =  currentTFormat.format(date);

            return converted_date;
        }catch (Exception e){ e.printStackTrace();}

        return converted_date;
    }

    public static String getCurrentTimeZone(){
        TimeZone tz = Calendar.getInstance().getTimeZone();
        System.out.println(tz.getDisplayName());
        return tz.getID();
    }

    public static String getCityName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (addresses == null)
            return "";
        return addresses.get(0).getLocality();
    }

    public static String getDistance(Double lat, Double lng) {
        float pk = (float) (180.f / 3.14159);

        float a1 = (float) (latitude / pk);
        float a2 = (float) (longitude / pk);
        float b1 = (float) (lat / pk);
        float b2 = (float) (lng / pk);

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);
        double distance = 6366000 * tt;
        DecimalFormat formater = new DecimalFormat("#.##");
        String distanceInMetres = formater.format(distance);
        double v = Double.parseDouble(distanceInMetres) / 1000;
        Log.d("DISTMCESS", formater.format(v) + "  ");
        return formater.format(v);
    }

    public static void onNavigationClick(Context context, String latitude, String longitude) {
        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    public static void onCallingIntent(Context context, String mobile) {
        Intent callIntent = new Intent(Intent.ACTION_VIEW);
        callIntent.setData(Uri.parse("tel:" + mobile));
        context.startActivity(callIntent);
    }

    public static void onEmailIntent(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Help");
        intent.setType("message/rfc822");
        Intent chooser = Intent.createChooser(intent, "Send email");
        context.startActivity(chooser);
    }

    public static void onWebIntent(Context context, String website) {
        Intent openURL = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(website);
        if (!website.startsWith("http://") && !website.startsWith("https://")) {
            uri = Uri.parse("http://" + website);
        }

        openURL.setData(uri);
        context.startActivity(openURL);
    }


    public static void dismissKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void setFocus(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static float getScreenHeight(Activity activity) {
        DisplayMetrics dimension = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dimension);
        int width = dimension.widthPixels;
        int height = dimension.heightPixels;
        return height;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            Log.d("CovrtBitmapExc", e.getMessage());
            return null;
        }
    }

    public static String getWeek(String DateDay)
    {
        String week = "";

        try {
            SimpleDateFormat toDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date date = toDateFormat.parse(DateDay);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

            System.out.println("Dha: "+dayOfWeek);
            if(dayOfWeek==1)
            {
                week = "SUN";
            }
            else if(dayOfWeek==2)
            {
                week = "MON";
            }
            else if(dayOfWeek==3)
            {
                week = "TUE";
            }
            else if(dayOfWeek==4)
            {
                week = "WED";
            }
            else if(dayOfWeek==5)
            {
                week = "THU";
            }
            else if(dayOfWeek==6)
            {
                week = "FRI";
            }
            else
            {
                week = "SAT";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return week;
    }


    public static String getDateInSystem(String Date) {
        String dateInSystem = "";

        String month = "" + Date.charAt(5)+Date.charAt(6);

        if(month.equals("01"))
        {
            dateInSystem = "Jan ";
        }
        else if(month.equals("02"))
        {
            dateInSystem = "Feb ";
        }
        else if(month.equals("03"))
        {
            dateInSystem = "Mar ";
        }
        else if(month.equals("04"))
        {
            dateInSystem = "Apr ";
        }
        else if(month.equals("05"))
        {
            dateInSystem = "May ";
        }
        else if(month.equals("06"))
        {
            dateInSystem = "Jun ";
        }
        else if(month.equals("07"))
        {
            dateInSystem = "Jul ";
        }
        else if(month.equals("08"))
        {
            dateInSystem = "Aug ";
        }
        else if(month.equals("09"))
        {
            dateInSystem = "Sep ";
        }
        else if(month.equals("10"))
        {
            dateInSystem = "Oct ";
        }
        else if(month.equals("11"))
        {
            dateInSystem = "Nov ";
        }
        else
        {
            dateInSystem = "Dec ";
        }

        dateInSystem = dateInSystem + Date.charAt(8)+Date.charAt(9);

        return dateInSystem;
    }


    public static String get24HrsByDate(String time) {

        long timestamp = (long)parseFloat(time);
        Date date = new Date(timestamp*1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        String java_date = sdf.format(date);

        String timer = java_date.substring(10,19);
        return timer;
    }

    public static String get24From12(String time)
    {
        DateFormat dfFrom = new SimpleDateFormat("HH:mm");
        DateFormat dfTo = new SimpleDateFormat("hh:mm a");
        try {
            Date date = dfFrom.parse(time);
            System.out.println("From Time- " + date);
            String changedTime = dfTo.format(date);
            System.out.println("To Time- " + changedTime);
            return changedTime;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }


    public static String get12HrsByDate(String time) {

        long timestamp = (long)parseFloat(time);
        Date date = new Date(timestamp*1000L);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss z");
        String java_date = sdf.format(date);

        String timer = java_date.substring(10,19);
        return timer;
    }

    public static int mps_to_kmph(double mps)
    {
        return (int) (3.6 * mps);
    }

    public static String celsiusToFahrenheit(double celsius) {
        double fahrenheit;
        DecimalFormat formatter = new DecimalFormat("##.00");
        fahrenheit=(9.0/5.0)*celsius + 32;
        return formatter.format(fahrenheit);
    }

    public static String mmToInches(String mm)
    {
        double inch = 0.0393701*parseFloat(mm);
        return String.format("%.2f", inch);
    }

    public static void recyclerViewAnimation(Activity activity, final RecyclerView recyclerView) {

        AnimationSet set = new AnimationSet(true);

        // Fade in animation
        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(400);
        fadeIn.setFillAfter(true);
        set.addAnimation(fadeIn);

        // Slide up animation from bottom of screen
        Animation slideUp = new TranslateAnimation(0, 0, getScreenHeight(activity), 0);
        slideUp.setInterpolator(new DecelerateInterpolator(4.f));
        slideUp.setDuration(400);
        set.addAnimation(slideUp);

        // Set up the animation controller              (second parameter is the delay)
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.2f);
        recyclerView.setLayoutAnimation(controller);

        // Set the adapter

    }

    public static void strictModePermission() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
// There are no active networks.
            return false;
        } else
            return true;
    }

    public static String getLatitude(String wholeString) {
        String[] picksplit = wholeString.split(",");
        String sourcelat = picksplit[picksplit.length - 2];
        sourcelat = sourcelat.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        Log.d("longitudechk", sourcelat);
        return sourcelat;
    }

    public static String getLongitude(String wholeString) {
        String[] picksplit = wholeString.split(",");
        String sourcelng = picksplit[picksplit.length - 1];
        sourcelng = sourcelng.replaceAll("[\\p{Ps}\\p{Pe}]", "");
        Log.d("longitudechk", sourcelng);
        return sourcelng;
    }

    public static Drawable getWeatherIcon(Context context, String icon) {

        String uri = "@drawable/" + "icon_" + icon; // where myresource (without the extension) is the file

        int imageResource = context.getResources().getIdentifier(uri,
                null,
                context.getPackageName());

        return context.getResources().getDrawable(imageResource);

    }
}

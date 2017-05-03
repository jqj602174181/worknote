@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class Utils {
 
    // �ֻ���������
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_CMWAP = 0x02;
    public static final int NETTYPE_CMNET = 0x03;
 
    public static boolean GTE_HC;
    public static boolean GTE_ICS;
    public static boolean PRE_HC;
    private static Boolean _hasBigScreen = null;
    private static Boolean _hasCamera = null;
    private static Boolean _isTablet = null;
    private static Integer _loadFactor = null;
 
    private static int _pageSize = -1;
    public static float displayDensity = 0.0F;
 
    static {
        GTE_ICS = Build.VERSION.SDK_INT >= 14;
        GTE_HC = Build.VERSION.SDK_INT >= 11;
        PRE_HC = Build.VERSION.SDK_INT >= 11 ? false : true;
    }
 
    public Utils() {
    }
    /**
     * ��ȡϵͳ��ǰ��ǰʱ���
      */
 
    public static String getTimesTamp() {
        long timestamp = System.currentTimeMillis()/1000;
        return String.valueOf(timestamp);
    }
 
    /**
     * dpֵ
     *
     * @param dp
     * @return
     */
    public static float dpToPixel(float dp) {
        return dp * (getDisplayMetrics().densityDpi / 160F);
    }
 
    public static int getDefaultLoadFactor() {
        if (_loadFactor == null) {
            Integer integer = Integer.valueOf(0xf & CXWYApplication.getContext()
                    .getResources().getConfiguration().screenLayout);
            _loadFactor = integer;
            _loadFactor = Integer.valueOf(Math.max(integer.intValue(), 1));
        }
        return _loadFactor.intValue();
    }
 
    /**
     * ��ȡ�ܶ�
     *
     * @return
     */
    public static float getDensity() {
        if (displayDensity == 0.0)
            displayDensity = getDisplayMetrics().density;
        return displayDensity;
    }
 
    /**
     * ��ȡ��Ļ����
     * {density=3.5, width=1440, height=2392, scaledDensity=3.5, xdpi=560.0, ydpi=560.0}
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) CXWYApplication.getContext().getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(
                displaymetrics);
        return displaymetrics;
    }
 
    /**
     * ��ȡ��Ļ�߶�
     *
     * @return
     */
    public static float getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }
 
    /**
     * ��ȡ��Ļ���
     *
     * @return
     */
 
    public static float getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }
 
    public static int[] getRealScreenSize(Activity activity) {
        int[] size = new int[2];
        int screenWidth = 0, screenHeight = 0;
        WindowManager w = activity.getWindowManager();
        Display d = w.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        d.getMetrics(metrics);
        // since SDK_INT = 1;
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17)
            try {
                screenWidth = (Integer) Display.class.getMethod("getRawWidth")
                        .invoke(d);
                screenHeight = (Integer) Display.class
                        .getMethod("getRawHeight").invoke(d);
            } catch (Exception ignored) {
            }
        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17)
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(d,
                        realSize);
                screenWidth = realSize.x;
                screenHeight = realSize.y;
            } catch (Exception ignored) {
            }
        size[0] = screenWidth;
        size[1] = screenHeight;
        return size;
    }
 
    public static int getStatusBarHeight() {
        Class<!--?--> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            return CXWYApplication.getContext().getResources()
                    .getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 
    /**
     * ��ȡΨһ��ʶ��
     * 9121e9b9-f0ee-4ea6-a9bd-802db5535f9b
     *
     * @return
     */
    public static String getUdid() {
 
        String udid = PreferencesUtils.getInstance().getString(CXWYApplication.getContext(),
                "udid", "");
        if (udid.length() == 0) {
            udid = String.format("%s", UUID.randomUUID());
            PreferencesUtils.getInstance().putString(CXWYApplication.getContext(), "udid", udid);
        }
        return udid;
    }
 
    /**
     * �Ƿ����
     *
     * @return
     */
 
    public static boolean hasBigScreen() {
        boolean flag = true;
        if (_hasBigScreen == null) {
            boolean flag1;
            if ((0xf & CXWYApplication.getContext().getResources()
                    .getConfiguration().screenLayout) >= 3)
                flag1 = flag;
            else
                flag1 = false;
            Boolean boolean1 = Boolean.valueOf(flag1);
            _hasBigScreen = boolean1;
            if (!boolean1.booleanValue()) {
                if (getDensity() <= 1.5F)
                    flag = false;
                _hasBigScreen = Boolean.valueOf(flag);
            }
        }
        return _hasBigScreen.booleanValue();
    }
 
    /**
     * �Ƿ�������
     *
     * @return
     */
    public static final boolean hasCamera() {
        if (_hasCamera == null) {
            PackageManager pckMgr = CXWYApplication.getContext()
                    .getPackageManager();
            boolean flag = pckMgr
                    .hasSystemFeature("android.hardware.camera.front");
            boolean flag1 = pckMgr.hasSystemFeature("android.hardware.camera");
            boolean flag2;
            if (flag || flag1)
                flag2 = true;
            else
                flag2 = false;
            _hasCamera = Boolean.valueOf(flag2);
        }
        return _hasCamera.booleanValue();
    }
 
    /**
     * �ж��Ƿ��������menu��
     *
     * @param context
     * @return
     */
    public static boolean hasHardwareMenuKey(Context context) {
        boolean flag = false;
        if (PRE_HC)
            flag = true;
        else if (GTE_ICS) {
            flag = ViewConfiguration.get(context).hasPermanentMenuKey();
        } else
            flag = false;
        return flag;
    }
 
    /**
     * �ж��Ƿ���GSM����
     * ��ҪȨ��   ACCESS_NETWORK_STATE
     *
     * @return
     */
    public static boolean hasInternet() {
        boolean flag;
        if (((ConnectivityManager) CXWYApplication.getContext().getSystemService(
                "connectivity")).getActiveNetworkInfo() != null) {
            flag = true;
        } else
            flag = false;
        return flag;
    }
 
    /**
     * �Ƿ���google�̵�
     *
     * @param activity
     * @param pck
     * @return
     */
    public static boolean gotoGoogleMarket(Activity activity, String pck) {
        try {
            Intent intent = new Intent();
            intent.setPackage("com.android.vending");
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + pck));
            activity.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
 
    /**
     * �жϳ����Ƿ�װ
     *
     * @param pckName
     * @return
     */
 
    public static boolean isPackageExist(String pckName) {
        try {
            PackageInfo pckInfo = CXWYApplication.getContext().getPackageManager()
                    .getPackageInfo(pckName, 0);
            if (pckInfo != null)
                return true;
        } catch (PackageManager.NameNotFoundException e) {
            KLog.e(e.getMessage());
        }
        return false;
    }
 
    /**
     * ���ض�����ͼ
     *
     * @param view
     */
    public static void hideAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(view.getWidth(), 0, 0, 0);
    }
 
    /**
     * ��ʾ������ͼ
     *
     * @param view
     */
    public static void showAnimatedView(View view) {
        if (PRE_HC && view != null)
            view.setPadding(0, 0, 0, 0);
    }
 
    /**
     * ��ʾ����dialog
     *
     * @param dialog
     */
    public static void showSoftKeyboard(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(4);
    }
 
    /**
     * ��ʾ����view
     *
     * @param view
     */
 
    public static void showSoftKeyboard(View view) {
        ((InputMethodManager) CXWYApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                InputMethodManager.SHOW_FORCED);
    }
 
    /**
     * �л�����
     *
     * @param view
     */
 
    public static void toogleSoftKeyboard(View view) {
        ((InputMethodManager) CXWYApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).toggleSoftInput(0,
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
 
    /**
     * ���ؼ���
     *
     * @param view
     */
 
    public static void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) CXWYApplication.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }
 
    /**
     * �ж��Ƿ����
     *
     * @return
     */
    public static boolean isLandscape() {
        boolean flag;
        if (CXWYApplication.getContext().getResources().getConfiguration().orientation == 2)
            flag = true;
        else
            flag = false;
        return flag;
    }
 
    /**
     * �ж��Ƿ�����
     *
     * @return
     */
 
    public static boolean isPortrait() {
        boolean flag = true;
        if (CXWYApplication.getContext().getResources().getConfiguration().orientation != 1)
            flag = false;
        return flag;
    }
 
    /**
     * �ж��Ƿ�ƽ��
     *
     * @return
     */
 
    public static boolean isTablet() {
        if (_isTablet == null) {
            boolean flag;
            if ((0xf & CXWYApplication.getContext().getResources()
                    .getConfiguration().screenLayout) >= 3)
                flag = true;
            else
                flag = false;
            _isTablet = Boolean.valueOf(flag);
        }
        return _isTablet.booleanValue();
    }
 
    /**
     * ��λת��
     *
     * @param f
     * @return
     */
    public static float pixelsToDp(float f) {
        return f / (getDisplayMetrics().densityDpi / 160F);
    }
 
    /**
     * �ж��Ƿ���sd��
     *
     * @return
     */
 
    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }
 
    /**
     * �ж�ϵͳ���Թ���
     *
     * @return
     */
    public static String getCurCountryLan() {
        return CXWYApplication.getContext().getResources().getConfiguration().locale
                .getLanguage()
                + "-"
                + CXWYApplication.getContext().getResources().getConfiguration().locale
                .getCountry();
    }
 
    /**
     * �ж��Ƿ����ļ��壨CN�������й�
     *
     * @return
     */
 
    public static boolean isZhCN() {
        String lang = CXWYApplication.getContext().getResources()
                .getConfiguration().locale.getCountry();
        if (lang.equalsIgnoreCase("CN")) {
            return true;
        }
        return false;
    }
 
    /**
     * ��ȡ�������İٷֱ�
     *
     * @param p1
     * @param p2
     * @return
     */
    public static String percent(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(5);//������С��λ��(����)
        str = nf.format(p3);
        return str;
    }
 
    public static String percent2(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        str = nf.format(p3);
        return str;
    }
 
    /**
     * �򿪱�app��Ӧ���̵��ҳ��
     *
     * @param context
     */
    public static void openAppInMarket(Context context) {
 
        if (context != null) {
            String pckName = context.getPackageName();
            try {
                String str = "market://details?id=" + pckName;
                Intent localIntent = new Intent("android.intent.action.VIEW");
                localIntent.setData(Uri.parse(str));
                context.startActivity(localIntent);
 
            } catch (Exception ex) {
 
            }
        }
    }
 
    /**
     * ȫ����ʾ��ȥ������״̬��
     *
     * @param activity
     */
    public static void setFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(params);
        activity.getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
 
    /**
     * �ر�ȫ����ʾ
     *
     * @param activity
     */
    public static void cancelFullScreen(Activity activity) {
        WindowManager.LayoutParams params = activity.getWindow()
                .getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(params);
        activity.getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
 
    /**
     * �õ�Ӧ�ð���
     *
     * @param pckName
     * @return
     */
    public static PackageInfo getPackageInfo(String pckName) {
        try {
            return CXWYApplication.getContext().getPackageManager()
                    .getPackageInfo(pckName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            KLog.e(e.getMessage());
        }
        return null;
    }
 
    /**
     * ���app�汾��
     *
     * @return
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            versionCode = CXWYApplication.getContext()
                    .getPackageManager()
                    .getPackageInfo(CXWYApplication.getContext().getPackageName(),
                            0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }
 
    public static int getVersionCode(String packageName) {
        int versionCode = 0;
        try {
            versionCode = CXWYApplication.getContext().getPackageManager()
                    .getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }
 
    /**
     * ��ȡ�汾����
     *
     * @return
     */
    public static String getVersionName() {
        String name = "";
        try {
            name = CXWYApplication.getContext()
                    .getPackageManager()
                    .getPackageInfo(CXWYApplication.getContext().getPackageName(),
                            0).versionName;
        } catch (PackageManager.NameNotFoundException ex) {
            name = "";
        }
        return name;
    }
 
    public static boolean isScreenOn() {
        PowerManager pm = (PowerManager) CXWYApplication.getContext()
                .getSystemService(Context.POWER_SERVICE);
        return pm.isScreenOn();
    }
 
    /**
     * ��װapk
     *
     * @param context
     * @param file
     */
    public static void installAPK(Context context, File file) {
        if (file == null || !file.exists())
            return;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
 
    /**
     * ��ð�ת��apk
     *
     * @param file
     * @return
     */
    public static Intent getInstallApkIntent(File file) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        return intent;
    }
 
    /**
     * ��绰
     *
     * @param context
     * @param number
     */
    public static void openDial(Context context, String number) {
        Uri uri = Uri.parse("tel:" + number);
        Intent it = new Intent(Intent.ACTION_DIAL, uri);
        context.startActivity(it);
    }
 
    /**
     * ������
     *
     * @param context
     * @param smsBody
     * @param tel
     */
 
    public static void openSMS(Context context, String smsBody, String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsBody);
        context.startActivity(it);
    }
 
    public static void openDail(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
 
    public static void openSendMsg(Context context) {
        Uri uri = Uri.parse("smsto:");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
 
    /**
     * ����ϵͳ���
     *
     * @param context
     */
    public static void openCamera(Context context) {
        Intent intent = new Intent(); // ���������
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        intent.setFlags(0x34c40000);
        context.startActivity(intent);
    }
 
    /**
     * ��ȡ�ƶ��豸��ʶ��
     * ��ҪȨ��android.permission.READ_PHONE_STATE
     *
     * @return
     */
    public static String getIMEI() {
        TelephonyManager tel = (TelephonyManager) CXWYApplication.getContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tel.getDeviceId();
    }
 
    /**
     * ����ֻ��ͺ�
     *
     * @return
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }
 
    /**
     * ���ֻ��ϰ�װ��ָ��������app
     *
     * @param context
     * @param packageName
     */
    public static void openApp(Context context, String packageName) {
        Intent mainIntent = CXWYApplication.getContext().getPackageManager()
                .getLaunchIntentForPackage(packageName);
        if (mainIntent == null) {
            mainIntent = new Intent(packageName);
        } else {
            KLog.i("Action:" + mainIntent.getAction());
        }
        context.startActivity(mainIntent);
    }
 
    public static boolean openAppActivity(Context context, String packageName,
                                          String activityName) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName cn = new ComponentName(packageName, activityName);
        intent.setComponent(cn);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    /**
     * �ж�wifi�Ƿ��
     *
     * @return
     */
    public static boolean isWifiOpen() {
        boolean isWifiConnect = false;
        ConnectivityManager cm = (ConnectivityManager) CXWYApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        // check the networkInfos numbers
        NetworkInfo[] networkInfos = cm.getAllNetworkInfo();
        for (int i = 0; i < networkInfos.length; i++) {
            if (networkInfos[i].getState() == NetworkInfo.State.CONNECTED) {
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_MOBILE) {
                    isWifiConnect = false;
                }
                if (networkInfos[i].getType() == ConnectivityManager.TYPE_WIFI) {
                    isWifiConnect = true;
                }
            }
        }
        return isWifiConnect;
    }
 
    /**
     * ж��ָ��������app
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        if (isPackageExist(packageName)) {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE,
                    packageURI);
            context.startActivity(uninstallIntent);
        }
    }
 
    @SuppressWarnings("deprecation")
    public static void copyTextToBoard(String string) {
        if (TextUtils.isEmpty(string))
            return;
        ClipboardManager clip = (ClipboardManager) CXWYApplication.getContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        clip.setText(string);
//  AppContext.showToast(R.string.copy_success);
    }
 
    /**
     * �����ʼ�
     *
     * @param context
     * @param subject ����
     * @param content ����
     * @param emails  �ʼ���ַ
     */
    public static void sendEmail(Context context, String subject,
                                 String content, String... emails) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            // ģ����
            // intent.setType("text/plain");
            intent.setType("message/rfc822"); // ���
            intent.putExtra(Intent.EXTRA_EMAIL, emails);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
 
    public static int getStatuBarHeight() {
        Class<!--?--> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 38;// Ĭ��Ϊ38��ò�ƴ󲿷���������
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = CXWYApplication.getContext().getResources()
                    .getDimensionPixelSize(x);
 
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }
 
    public static int getActionBarHeight(Context context) {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
                tv, true))
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
 
        if (actionBarHeight == 0
                && context.getTheme().resolveAttribute(R.attr.actionBarSize,
                tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
 
        return actionBarHeight;
    }
 
    public static boolean hasStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        if ((attrs.flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN) {
            return false;
        } else {
            return true;
        }
    }
 
    /**
     * ����ϵͳ��װ�˵�Ӧ�÷���
     *
     * @param context
     * @param title
     * @param url
     */
    public static void showSystemShareOption(Activity context,
                                             final String title, final String url) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "����" + title);
        intent.putExtra(Intent.EXTRA_TEXT, title + " " + url);
        context.startActivity(Intent.createChooser(intent, "ѡ�����"));
    }
 
    /**
     * ��ȡ��ǰ��������
     *
     * @return 0��û������ 1��WIFI���� 2��WAP���� 3��NET����
     */
    public static int getNetworkType() {
        int netType = 0;
        ConnectivityManager connectivityManager = (ConnectivityManager) CXWYApplication.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType;
        }
        int nType = networkInfo.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = networkInfo.getExtraInfo();
            if (!TextUtils.isEmpty(extraInfo)) {
                if (extraInfo.toLowerCase().equals("cmnet")) {
                    netType = NETTYPE_CMNET;
                } else {
                    netType = NETTYPE_CMWAP;
                }
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }
 
 
}
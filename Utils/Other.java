public class Other {

    public Other() {
        mScheduledExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Instrumentation ins = new Instrumentation();
                ins.sendKeyDownUpSync(KeyEvent.KEYCODE_HOME);
            }
        });
    }

}
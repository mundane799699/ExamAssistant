package com.mundane.examassistant.ui.activity;

import com.mundane.examassistant.base.BaseActivity;

public class SplashActivity extends BaseActivity {

//    private SiXiuQuestionDao    mSiXiuQuestionDao;
//    private List<SiXiuQuestion> mSiXiuQuestionList;
//    private Handler mHandler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    Toast.makeText(SplashActivity.this, "数据导入完成", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    finish();
//                    break;
//            }
//        }
//    };
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//
////        checkData();
//
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        if (mHandler != null) {
//            mHandler.removeCallbacksAndMessages(null);
//            mHandler = null;
//        }
//        super.onDestroy();
//    }
//
//
////    private void checkData() {
////        mSiXiuQuestionDao = DbHelper.getSiXiuQuestionDao();
////        mSiXiuQuestionList = mSiXiuQuestionDao.loadAll();
////        if (mSiXiuQuestionList.isEmpty()) {
////            new DbThread().start();
////        } else {
////            startActivity(new Intent(this, MainActivity.class));
////            finish();
////        }
////    }
////
////    private class DbThread extends Thread {
////        @Override
////        public void run() {
////            AssetManager assetManager = getAssets();
////            try {
////                String[] arr = assetManager.list("resource");
////                for (String fileName : arr) {
////                    inputDataToDB(assetManager, fileName);
////                }
////                mHandler.sendEmptyMessage(0);
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////    }
////
////    private void inputDataToDB(AssetManager assetManager, String fileName) throws Exception {
////            InputStream inputStream = assetManager.open("resource/" + fileName);
////            String text = FileUtils.readStringFromInputStream(inputStream);
////            //LogUtils.d(text);
////            //LogUtils.d(fileName);
////            QuestionBean questionBean = GsonUtil.parseJsonToBean(text, QuestionBean.class);
////            List<QuestionBean.PlistBean.ArrayBean.DictBean> dictBeanList = questionBean.plist.array.dict;
////            String firstChar = fileName.substring(0, 1);
////            for (int i = 0; i < dictBeanList.size(); i++) {
////                inputDataToDbByFirstChar(firstChar, dictBeanList.get(i), i);
////            }
////
////    }
//
//    private void inputDataToDbByFirstChar(String firstChar, QuestionBean.PlistBean.ArrayBean.DictBean dictBean, int index) {
////        List<String> string = dictBean.string;
////        switch (firstChar) {
////            case "思":
////                SiXiuQuestion siXiuQuestion;
////                if (string.size() == 6) {
////                    siXiuQuestion = new SiXiuQuestion(null, string.get(5), string.get(0), string.get(1), string.get(2), string.get(3), string.get(4), false, false);
////                } else {
////                    siXiuQuestion = new SiXiuQuestion(null, string.get(3), string.get(0), string.get(1), null, null, string.get(2), false, false);
////                }
////                mSiXiuQuestionDao.insert(siXiuQuestion);
////                break;
////            case "毛":
////                MaoGaiXiaQuestion maoGaiXiaQuestion;
////                if (string.size() == 6) {
////                    maoGaiXiaQuestion = new MaoGaiXiaQuestion(null, string.get(5), string.get(0), string.get(1), string.get(2), string.get(3), string.get(4), false, false);
////                } else {
////                    maoGaiXiaQuestion = new MaoGaiXiaQuestion(null, string.get(3), string.get(0), string.get(1), null, null, string.get(2), false, false);
////                }
////                DbHelper.getMaoGaiXiaQuestionDao().insert(maoGaiXiaQuestion);
////                break;
////            case "近":
////                JinDaiShiQuestion jinDaiShiQuestion;
////                if (string.size() == 6) {
////
////                    jinDaiShiQuestion = new JinDaiShiQuestion(null, string.get(5), string.get(0), string.get(1), string.get(2), string.get(3), string.get(4), false, false);
////                } else {
////                    jinDaiShiQuestion = new JinDaiShiQuestion(null, string.get(3), string.get(0), string.get(1), null, null, string.get(2), false, false);
////                }
////                DbHelper.getJinDaiShiQuestionDao().insert(jinDaiShiQuestion);
////                break;
////            case "马":
////                MaKeSiQuestion maKeSiQuestion;
////                if (string.size() == 6) {
////                    maKeSiQuestion = new MaKeSiQuestion(null, string.get(5), string.get(0), string.get(1), string.get(2), string.get(3), string.get(4), false, false);
////                } else {
////                    maKeSiQuestion = new MaKeSiQuestion(null, string.get(3), string.get(0), string.get(1), null, null, string.get(2), false, false);
////                }
////                DbHelper.getMaKeSiQuestionDao().insert(maKeSiQuestion);
////                break;
////        }
//    }
}

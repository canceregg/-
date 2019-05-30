package com.example.skyreach.Tool;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.skyreach.Ob.EOne;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLDownloadTask;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.cos.xml.transfer.TransferState;
import com.tencent.cos.xml.transfer.TransferStateListener;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import org.greenrobot.eventbus.EventBus;

import static com.example.skyreach.YL.getContext;


/**
 * Created by Anizwel on 2018/10/21.
 */

public class TXCOS {
    private static CosXmlSimpleService cosXml;
    private static TransferConfig transferConfig;
    private static TransferManager transferManager;
    private static Context context=getContext();
    public  TXCOS(){
        //初始化配置类
        //CosXmlServiceConfig 是 COS 服务的配置类，您可以使用如下代码来初始化：
        String appid = "1257388698";
        String region = "ap-guangzhou";

//创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .setDebuggable(true)
                .builder();

        //初始化授权类
        //QCloudCredentialProvider 是 COS 服务的授权类，可以给请求添加签名来认证您的身份。

        String secretId = "AKIDG3mLkz8x0NmHfHjYHWq6GNrCPVSEsFVc";
        String secretKey ="BDCA876446AGHBvUsCRXSFrRr6A32veC";

/**
 * 初始化 {@link QCloudCredentialProvider} 对象，来给 SDK 提供临时密钥。
 */
        QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId,
                secretKey, 300);

        //初始化 COS 服务类
        //CosXmlService 是 COS 服务类，可用来操作各种 COS 服务，当您实例化配置类和授权类后，您可以很方便的实例化一个 COS 服务类，具体代码如下：
        //CosXmlService cosXmlService = new CosXmlService(context, serviceConfig, credentialProvider);
        cosXml=new CosXmlSimpleService(context,serviceConfig,credentialProvider);

        // 初始化 TransferConfig
        transferConfig = new TransferConfig.Builder().build();

        //初始化 TransferManager
        transferManager = new TransferManager(cosXml, transferConfig);

    }
    public  TransferManager getTransferManager(Context context){
        return transferManager;
    }

    //locPath:文件的本地路径
    //cosPath:COS绝对路径
    public void up(String locPath,String cosPath){//上传文件

/**
 若有特殊要求，则可以如下进行初始化定制。如限定当文件 >= 2M 时，启用分片上传，且分片上传的分片大小为 1M, 当源文件大于 5M 时启用分片复制，且分片复制的大小为 5M。
 TransferConfig transferConfig = new TransferConfig.Builder()
 .setDividsionForCopy(5 * 1024 * 1024) // 是否启用分片复制的文件最小大小
 .setSliceSizeForCopy(5 * 1024 * 1024) //分片复制时的分片大小
 .setDivisionForUpload(2 * 1024 * 1024) // 是否启用分片上传的文件最小大小
 .setSliceSizeForCopy(1024 * 1024) //分片上传时的分片大小
 .build();
 */
        String bucket = "flowind-1257388698";
        //，即存储到 COS 上的绝对路径; //格式如 cosPath = "test.txt";
        String srcPath = locPath; // 如 srcPath=Environment.getExternalStorageDirectory().getPath() + "/test.txt";
        String uploadId = null;//用于续传，若无，则为null.
//上传文件
        COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);
//设置上传进度回调
        cosxmlUploadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
            @Override
            public void onProgress(long complete, long target) {
                float progress = 1.0f * complete / target * 100;

                //handler.sendMessage(message);
            }
        });
//设置返回结果回调
        cosxmlUploadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                EventBus.getDefault().post(new EOne("up","success"));
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                EventBus.getDefault().post(new EOne("up","fail"));
            }
        });
//设置任务状态回调, 可以查看任务过程
        cosxmlUploadTask.setTransferStateListener(new TransferStateListener() {
            @Override
            public void onStateChanged(TransferState state) {

            }
        });
        //cosxmlUploadTask.run();

/**
 若有特殊要求，则可以如下操作：
 PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
 putObjectRequest.setRegion(region); //设置存储桶所在的园区
 putObjectRequest.setSign(600); //设置签名sign有效期
 putObjectRequest.setNeedMD5(true); //是否启用Md5校验
 COSXMLUploadTask cosxmlUploadTask = transferManager.upload(putObjectRequest, uploadId);
 */

//取消上传
        // cosxmlUploadTask.cancel();



//暂停上传
        //cosxmlUploadTask.pause();

//恢复上传
        //cosxmlUploadTask.resume();
    }

    //cosPath
    //locDir:本地文件夹目录
    //locName:储存在本地的名字，
    public void downLoad(String cosPath,String locDir,String locName){

        String bucket = "flowind-1257388698"; //文件所在的存储桶
        String savedDirPath = locDir;
        String savedFileName = locName;//若不填（null）,则与 cos 上的文件名一样
//下载文件
        COSXMLDownloadTask cosxmlDownloadTask = transferManager.download(context, bucket, cosPath, savedDirPath, savedFileName);
//设置下载进度回调
        cosxmlDownloadTask.setCosXmlProgressListener(new CosXmlProgressListener() {
            @Override
            public void onProgress(long complete, long target) {
                float progress = 1.0f * complete / target * 100;
            }
        });
//设置返回结果回调
        cosxmlDownloadTask.setCosXmlResultListener(new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                EventBus.getDefault().post(new EOne("download","success"));
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {
                EventBus.getDefault().post(new EOne("download","fail"));
            }
        });
//设置任务状态回调, 可以查看任务过程
        cosxmlDownloadTask.setTransferStateListener(new TransferStateListener() {
            @Override
            public void onStateChanged(TransferState state) {
                Log.d("TEST", "Task state:" + state.name());
            }
        });

/**
 若有特殊要求，则可以如下操作：
 GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, cosPath, localDir, localFileName);
 getObjectRequest.setRegion(region); //设置存储桶所在的园区
 getObjectRequest.setSign(600); //设置签名 sign 有效期
 COSXMLDownloadTask cosxmlDownloadTask = transferManager.download(context, getObjectRequest);
 */

//取消下载
        //cosxmlDownloadTask.cancel();

//暂停下载
        //cosxmlDownloadTask.pause();

//恢复下载
        //cosxmlDownloadTask.resume();
    }
}

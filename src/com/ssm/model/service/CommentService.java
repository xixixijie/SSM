package com.ssm.model.service;


import com.ssm.model.bean.CommentInfo;
import com.ssm.model.bean.Keyword;
import com.ssm.model.dao.CommentDAO;
import com.ssm.model.dao.KeywordDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * Created by xixi on 2018/7/19.
 */
@Service
public class CommentService {
    @Autowired
    private KeywordDAO keywordDAO;

    @Autowired
    private  CommentDAO commentDAO;

    public List<CommentInfo> getCommentByKey(String keyName){
        System.out.println("-----通过关键词获取评论service-----");
        List<CommentInfo> list=commentDAO.getCommentByKey(keyName);
        System.out.println("通过关键词获取评论的数量"+list.size());
        return list;
    }

    public void addComment(CommentInfo comment){
        System.out.println("-----提交评论service-----");
        //分析评论

        analysComment(comment);



    }

    private void analysComment(CommentInfo comment){
        System.out.println("-----分析service-----");

        String content=comment.getCtext();
        System.out.println(content);

        //先判断是否已经包含了已有关键词
        //包含了就不用调用api
        List<Keyword> keywordList=keywordDAO.getAllKeyword();
        Set<String> disSet=new HashSet<>();

        System.out.println("关键词大小"+keywordList.size());
        for(Keyword k:keywordList){
            if(content.contains(k.getKeyName())){
                //key出现次数+1
                Map<String,Integer> map=new HashMap<>();
                map.put("keyid",k.getKeyID());
                map.put("keynum",k.getKeyNum()+1);
                keywordDAO.addNum(map);
                return;
            }
        }
        //不包含
        String adj=getAdj(content);
        if(adj.equals("fail")){
            System.out.println("-----分词失败------");
        }else{
            System.out.println("分词的形容词"+adj);
            Keyword keyword=new Keyword();
            keyword.setKeyName(adj);
            keyword.setKeyNum(1);
            keywordDAO.addKeyword(keyword);
        }



    }


    //分词
    private static String getAdj(String evaluate){
        String text=null;

        String type= null;
        try {
            text= URLEncoder.encode(evaluate ,"UTF-8");
            type = URLEncoder.encode("13","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String appid="4515f4c048cabf629ebd16c3197a5ee2";

        String httpUrl = "https://api.shenjian.io/nlp/comment_tag/";

        String httpArg = "appid="+appid+"&text="+text+"&type="+type;

        String jsonResult = request(httpUrl, httpArg);

        System.out.println(jsonResult);

        int from0=jsonResult.indexOf("error_code");
        int to0=jsonResult.indexOf(",");
        if(jsonResult.substring(from0+12,to0).equals("0")){
            System.out.println(jsonResult);
            int from=jsonResult.indexOf("adj");
            int to=jsonResult.indexOf("sentiment");
            String adj=jsonResult.substring(from+6,to-3);

            return adj;
        }else {
            return "fali";
        }

    }



    private static String request(String httpUrl, String httpArg) {

        BufferedReader reader = null;

        String result = null;

        StringBuffer sbf = new StringBuffer();

        httpUrl = httpUrl + "?" + httpArg;

        try {

            URL url = new URL(httpUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            connection.setRequestProperty("charset", "utf-8");

            connection.setRequestProperty("Accept-Encoding", "gzip");

            connection.connect();

            InputStream is = new GZIPInputStream(connection.getInputStream());

            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String strRead = null;

            while ((strRead = reader.readLine()) != null) {

                sbf.append(strRead);

                sbf.append("\r\n");

            }

            reader.close();

            result = sbf.toString();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return result;

    }
}

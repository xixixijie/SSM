package com.ssm.model.service;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ssm.model.bean.*;
import com.ssm.model.dao.CommentDAO;
import com.ssm.model.dao.KeywordDAO;
import com.ssm.util.FileUtil;
import nu.xom.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    /**
     * 根据关键词获得评论
     * @param labelID
     * @return
     */

    public List<CommentInfo> getCommentByKey(String labelID){
        System.out.println("-----通过关键词获取评论service-----");


        List<Keyword> keywords=keywordDAO.getKeywordByLabelID(Integer.parseInt(labelID));

        List<CommentInfo> list=new ArrayList<>();
        for(Keyword keyword:keywords){
            System.out.println(keyword.getKeyName());
            List<CommentInfo> temp=commentDAO.getCommentByKey(keyword.getKeyName());
            System.out.println(temp.size());
            list.addAll(temp);
        }
        System.out.println("通过关键词获取评论的数量"+list.size());
        return list;
    }

    /**
     * 添加评论
     * @param comment
     */

    public void addComment(CommentInfo comment){
        System.out.println("-----提交评论service-----");
        //分析评论

        analysComment(comment);
    }

    /**
     * 分析评论
     * @param comment
     */

    private void analysComment(CommentInfo comment){
        System.out.println("-----分析评论service-----");

        //写入语料库

        String content=comment.getCtext();
        //System.out.println(content);
        //写入语料库
        FileUtil.write(content,"comments.txt");


        //先判断是否已经包含了已有关键词
        //包含了就不用调用api
        List<Keyword> keywordList=keywordDAO.getAllKeyword();
        Set<String> disSet=new HashSet<>();

        //System.out.println("关键词大小"+keywordList.size());
        for(Keyword k:keywordList){
            if(content.contains(k.getKeyName())){
                //key出现次数+1

                Map<String,Integer> map=new HashMap<>();

                map.put("keyid",k.getKeyID());
                map.put("keynum",k.getKeyNum()+1);
                keywordDAO.addNum(map);
                int labelID=k.getLabelID();
                keyLabel label=keywordDAO.getKeyLabel(labelID);
                label.setLabelNum(label.getLabelNum()+1);

                keywordDAO.updateLabel(label);


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


    /**
     * 通过神箭手接口 获得形容词
     * @param evaluate
     * @return
     */
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


    /**
     * 神箭手接口请求函数
     * @param httpUrl
     * @param httpArg
     * @return
     */
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



    /*
     * the following,Created by WangKing on 2018/7/20.
     * */
    //用于测试DAO层的相关方法
    public void test(){
        System.out.println("---------in SERVICE TEST----------");
        CommentInfo c = new CommentInfo();
        {
            c.setCid(2);
            c.setUserID(10);
            c.setCtext("test");
            c.setPraise(200);
            c.setProduct_id(2);
            c.setCdate(new Date());
            c.setScore(0);
        }
        commentDAO.updateComment(c);
        commentDAO.saveComment(c);

//      Comment_photo cp = new Comment_photo();
//      cp.setCid(1);
//      cp.setPhotourl("//test");
//      commentDAO.savePhoto(cp);

//      Reply r = new Reply();
//      r.setRdate(new Date());
//      r.setCid(1);
//      r.setRtext("reply test1");
//      r.setUserid(1);
//      commentDAO.saveReply(r);

//        Praise p1 = new Praise();
//        {
//            p1.setCid(1);
//            p1.setUserid(2);
//            p1.setIs_praise(0);
//            commentDAO.updatePraise(p1);
//        }
//        Praise p2=commentDAO.getPraise(p1);
//        {
//            if(p2!=null) {
//                System.out.println("Praise_id--" + p2.getPraise_id());
//                System.out.println("Userid--" + p2.getUserid());
//                System.out.println("Cid--" + p2.getCid());
//                System.out.println("Is_praise--" + p2.getIs_praise());
//            }else{
//                System.out.println(p2);
//            }
//
//        }

//        Reply r = new Reply();
//        {
//            r.setUserid(1);
//            r.setRtext("savereply  test");
//            r.setCid(1);
//            r.setRdate(new Date());
//        }
//        commentDAO.saveReply(r);

        System.out.println("---------END  SERVICE getAllComment----------");

    }

    //根据点赞数获得评论
    //2018年7月30日,测试成功
    public List<CommentInfo> getPopularComment(int pageNum,int product_id,int userID){
        System.out.println("in SERVICE getPopularComment");

        //实际功能
        //得到评论,外带分页(分页未测试，2018年8月1日)
        Page<CommentInfo> page = PageHelper.startPage(pageNum,5);//每页五条
        // List<CommentInfo> list = commentDAO.getPopularComment(product_id);
        System.out.println("---------product_id"+product_id);
        commentDAO.getPopularComment(product_id);
        List<CommentInfo> list = page.getResult();
        for(CommentInfo c:list){
            int cid =c.getCid();
            //获得图片
            System.out.println("------into commentDAO.getCommentPhoto(cid)");
            c.setCphoto(commentDAO.getCommentPhoto(cid));
            //获得回复
            System.out.println("------into commentDAO.getReply(cid)");
            c.setReply(commentDAO.getReply(cid));
        }
        //该用户是否为该评论点赞
        for(CommentInfo c:list){
            Praise p =new Praise();
            {
                p.setUserid(userID);
                p.setCid(c.getCid());
            }
            System.out.println("------into commentDAO.getPraise(p)");
            c.setPraiseInfo(commentDAO.getPraise(p));

        }

        //       伪造数据
//        List<CommentInfo> list = new ArrayList<CommentInfo>();
//        if(pageNum<2) {
//            for (int i = 0; i < 5; i++) {
//                CommentInfo c = new CommentInfo();
//                c.setCid(i + 5 * pageNum + 1);
//                c.setUserName(i + "Cusername");
//                c.setUserID(i + 5 * pageNum + 1);
//                c.setScore((i + 5 * pageNum + 1) * 10);
//                c.setCtext("cteXt teSt :123456123456");
//                c.setCdate(new Date());
//                c.setPraise(666);
//                List<Comment_photo> cplist = new ArrayList<Comment_photo>();
//                for (int j = 0; j < 3; j++) {
//                    System.out.println("----------set cphoto-------");
//                    Comment_photo cp = new Comment_photo();
//                    cp.setPhotourl("1533016464521WallpaperStudio10-589.jpg");
//                    cplist.add(cp);
//                }
//                c.setCphoto(cplist);
//                List<Reply> rlist = new ArrayList<Reply>();
//                for (int j = 0; j < 3; j++) {
//                    Reply r = new Reply();
//                    r.setUserName("RUserName");
//                    r.setRtext("R teXt teSt abcdefg");
//                    rlist.add(r);
//                }
//                c.setReply(rlist);
//                list.add(c);
//            }
//
//        }else{
//            list=null;
//        }


        return list;//将获取的每次返回5条信息返回.subList((pageNum-1)*5,pageNum*5)
    }

    //保存评论
    @Transactional
    public void saveComment(CommentInfo comment, MultipartFile[] upload, String uploadpath){
       //cxj
        analysComment(comment);

        System.out.println("in SERVICE saveComment");
        //文件上传
        System.out.println("------uploadpath2"+uploadpath+"------");
        for(int i=0; i<upload.length;i++)
        {
            String filename = System.currentTimeMillis()+upload[i].getOriginalFilename();
            File f = new File(uploadpath,filename);
            try {
                upload[i].transferTo(f);
            } catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(i==0)
            {
                comment.setPraise(0);
                comment.setCdate(new Date());
                //2. 保存comment
                System.out.println("----------commentDAO.saveComment(comment)--------");
                commentDAO.saveComment(comment);
                comment.setCid(commentDAO.getMaxCid());
            }
            System.out.println("----------"+comment.getCid()+"--------");

            //保存图片
            Comment_photo photo = new Comment_photo();
            photo.setCid(comment.getCid());
            photo.setPhotourl(filename);
            System.out.println("----------savePhoto(photo)--------");
            commentDAO.savePhoto(photo);
        }
    }

    //保存回复
    @Transactional
    public void saveReply(Reply reply){
        System.out.println("in SERVICE saveReply");
        reply.setRdate(new Date());
        commentDAO.saveReply(reply);
        return;
    }

    //修改点赞内容，评论点赞数的+-和用户点赞情况修改
    //2018年7月30日，测试成功，大概
    @Transactional
    public void changePraise(int cid,int userID){
        System.out.println("--------in SERVICE changePraise------------");
        System.out.println("--------调用commentDAO.getCommentById(cid,userID)------------");
        CommentInfo comment = commentDAO.getCommentById(cid);
        Praise p0= new Praise();
        p0.setCid(cid);
        p0.setUserid(userID);
        System.out.println("--------调用commentDAO.getPraise(p0)------------");
        comment.setPraiseInfo(commentDAO.getPraise(p0));
        if(comment.getPraiseInfo()==null){
            //该用户未进行过点赞
            comment.setPraise(comment.getPraise()+1);
            Praise p = new Praise();
            p.setCid(comment.getCid());
            p.setUserid(userID);
            p.setIs_praise(1);
            System.out.println("--------调用commentDAO.savePraise(p)------------");
            commentDAO.savePraise(p);
        }else if(comment.getPraiseInfo().getIs_praise()==0){
            //该用户取消过点赞
            comment.setPraise(comment.getPraise()+1);
            comment.getPraiseInfo().setIs_praise(1);
            System.out.println("--------调用 commentDAO.updatePraise(comment.getPraiseInfo())------------");
            commentDAO.updatePraise(comment.getPraiseInfo());

        }else{
            //该用户已经进行过点赞
            comment.setPraise(comment.getPraise()-1);
            comment.getPraiseInfo().setIs_praise(0);
            System.out.println("--------调用 commentDAO.updatePraise(comment.getPraiseInfo())------------");
            commentDAO.updatePraise(comment.getPraiseInfo());
        }
        commentDAO.updateComment(comment);
    }

    //获取用户对某一评论的点赞情况
    public Praise getPraise(Praise praise){
        System.out.println("in SERVICE getPraise");
        return commentDAO.getPraise(praise);
    }

    /*wym,end*/
}

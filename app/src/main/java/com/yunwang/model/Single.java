package com.yunwang.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/22.
 */

public class Single {

    public static Single single;
    public ArrayList<DPloadModel> dPloadModels;
    public FailProject failProject;
    public  ArrayList<JCXM> jcxms;
    public  ArrayList<PhotoLoadModel> photoLoadModels;
    public  ArrayList<PhotoType> photoTypes;
    public  ArrayList<ProjectImage> projectImages;
    public  ArrayList<UnCheckProject> unCheckProjects;

    public static Single newInstance()
    {
        if (single == null)
            single = new Single();
        return single;
    }
}

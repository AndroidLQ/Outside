package com.yunwang.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/22.
 */

public class ManListItem implements Serializable{

    public String cllxdm;
    public String cllxmc;
    public String clsbdh = "";
    public List<CyzpItem> cyzps = null;
    public List<DpdtItem> dpdtItems;
    public String dpdtxm;
    public List<Dpdtzp> dpdtzp;
    public List<Dpjcxm> dpjcxms;
    public List<Dpjczp> dpjczps;
    public String hphm = "";
    public String hpzl = "";
    public String hpzlmc;
    public String jcxdh;
    public String jycs = "";
    public String jyjgbh = "";
    public String jylsh = "";
    public String jyxm;
    public Map<String, String> lsjyxm;
    public String lsxm = "";
    public List<CyzpItem> lszps;
    public String pdyj = "";
    public String rwgy;
    public String rwms;
    public String rwzxzt;
    public String syxzdm;
    public String syxzmc;
    public List<WgjcxmItem> wgjcxms;
}

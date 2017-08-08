package Project;

/**mainclass for materials**/
public class Material
{
    public Material(){}

    private Integer materialID = null;
    private String materialName = null;
    private Double materialPrice = null;
    private Integer materialAmount = null;

    public Integer getMaterialID(){return this.materialID;}
    public String getMaterialName(){return this.materialName;}
    public Double getMaterialPrice(){return this.materialPrice;}
    public Integer getMaterialAmount() {return this.materialAmount;}

    public void setMaterialID(Integer _materialID){this.materialID = _materialID;}
    public void setMaterialName(String _materialName){this.materialName = _materialName;}
    public void setMaterialPrice(Double _materialPrice){this.materialPrice = _materialPrice;}
    public void setMaterialAmount(Integer _materialAmount) {this.materialAmount = _materialAmount;}



}

package flatSpace.backendData.mechanics;

public class ResourceSet implements Cloneable {
  public static final int numResources = 8;
  private int hydrogen;
  private int lithium;
  private int tungsten;
  private int uranium;
  private int iron;
  private int silicon;
  private int carbon;
  private int ice;

  public ResourceSet(int h, int li, int w, int u, int fe, int si, int c, int h2o) {
    hydrogen = h;
    lithium = li;
    tungsten = w;
    uranium = u;
    iron = fe;
    silicon = si;
    carbon = c;
    ice = h2o;
  }
  public ResourceSet(int resources[]) {
    hydrogen = resources[0];
    lithium = resources[1];
    tungsten = resources[2];
    uranium = resources[3];
    iron = resources[4];
    silicon = resources[5];
    carbon = resources[6];
    ice = resources[7];
  }

  public ResourceSet() {
	  hydrogen = 0;
	    lithium = 0;
	    tungsten = 0;
	    uranium = 0;
	    iron = 0;
	    silicon = 0;
	    carbon = 0;
	    ice = 0;
	}

public ResourceSet clone() {
    return new ResourceSet(getAll());
  }
  public boolean add(ResourceSet aSet) {
    if (aSet == null) {
      return true;
    }
    int resources[] = new int[numResources];
    int all[] = this.getAll();
    int setAll[] = aSet.getAll();
    for(int i = 0;i<resources.length;++i) {
      resources[i] = all[i]+setAll[i];
      if (resources[i]<0) {
        return false;
      }
    }
    this.setAll(resources);
    return true;
  }

  public boolean subtract(ResourceSet aSet) {
    if (aSet == null) {
      return true;
    }
    int resources[] = new int[numResources];
    int all[] = this.getAll();
    int set[] = aSet.getAll();
    for(int i = 0;i<resources.length;++i) {
      resources[i] = all[i]-set[i];
      if (resources[i]<0) {
        return false;
      }
    }
    this.setAll(resources);
    return true;
  }
  public ResourceSet multiplyAll(double m) {
    int resources[] = new int[numResources];
    int all[] = this.getAll();
    for(int i = 0;i<resources.length;++i) {
      resources[i] = (int) (all[i]*m);
      if (resources[i]<0) {
        return null;
      }
    }
    this.setAll(resources);
    return this;
  }

  /**
  * send resources from one set to another
  * @param aSet destination of the resources
  * @param amount total amount of resources to send
  **/
  public void send(ResourceSet aSet, int amount) {
    int all[] = this.getAll();
    int total = 0;
    for(int i = 0;i<all.length;++i) {
      total+=all[i];
    }
    if (total == 0) {
      return;
    }
    if (total<=amount) {
      aSet.add(this);
      this.subtract(this);
      return;
    }
    int sent[] = new int[numResources];
    double prop[] = new double[numResources];
    prop[0] = hydrogen/total;
    prop[1] = lithium/total;
    prop[2] = tungsten/total;
    prop[3] = uranium/total;
    prop[4] = iron/total;
    prop[5] = silicon/total;
    prop[6] = carbon/total;
    prop[7] = ice/total;

    for(int i = 0; i<prop.length;++i) {
      sent[i] = (int) Math.floor(prop[i]*amount);
    }
    int remain = amount;
    for(int i = 0; i<sent.length;++i) {
      remain -= sent[i];
    }
    if (remain>0) {
      for(int i = 0;i<prop.length;++i) {
        prop[i]=prop[i]*amount - Math.floor(prop[i]*amount);
      }
      while(remain>0) {
        double largest = 0;
        int position = 0;
        for(int i = 0; i<prop.length;++i) {
          if (prop[i]>largest) {
            largest = prop[i];
            position = i;
          }
        }
        prop[position] = 0;
        ++sent[position];
        --remain;
      }
    }

    ResourceSet sentResources = new ResourceSet(sent);
    aSet.add(sentResources);
    this.subtract(sentResources);
  }

  /**
  * mine resources from planet and dump in stockpile
  * @param aSet planet's stockpile
  * @param mineRate rate of mining on colony
  * @param availability availability of resources
  **/
  public void mine(ResourceSet aSet, int mineRate, Availability availability) {
    int mined[] = new int[numResources];
    int all[] = this.getAll();
    int availabilities[] = availability.getAll();
    for(int i = 0;i<mined.length;++i) {
      if (all[i]<=0) {
        mined[i] = 0;
        availabilities[i] = 0;
      } else {
        mined[i]=(int) (mineRate*availabilities[i]/100.0);
        if (mined[i]>all[i]) {
          mined[i] = all[i];
          availabilities[i] = 0;
        }
      }
    }


    ResourceSet minedResources = new ResourceSet(mined);
    aSet.add(minedResources);
    this.subtract(minedResources);
  }

  public int getH() {
    return hydrogen;
  }
  public int getLi() {
    return lithium;
  }
  public int getW() {
    return tungsten;
  }
  public int getUr() {
    return uranium;
  }
  public int getFe() {
    return iron;
  }
  public int getSi() {
    return silicon;
  }
  public int getC() {
    return carbon;
  }
  public int getIce() {
    return ice;
  }
  public int[] getAll() {
    int all[] = new int[numResources];
    all[0] = hydrogen;
    all[1] = lithium;
    all[2] = tungsten;
    all[3] = uranium;
    all[4] = iron;
    all[5] = silicon;
    all[6] = carbon;
    all[7] = ice;
    return all;
  }
  public void setAll(int[] all) {
    hydrogen = all[0];
    lithium = all[1];
    tungsten = all[2];
    uranium = all[3];
    iron = all[4];
    silicon = all[5];
    carbon = all[6];
    ice = all[7];
  }
}

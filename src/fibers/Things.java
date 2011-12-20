/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fibers;

import java.util.ArrayList;

/**
 *
 * @author human
 */
public class Things {

  public static int ndims_init = 1;// 3 dimensions.  First dimension is a gimme, becuase it is the output/fire value.
  /* *************************************************************************************************** */

  public static class PointNd {

    public int ndims = 3;
    public int ninputs = ndims - 1;
    public double[] loc = null;

    public PointNd(int num_dims) {
      ndims = num_dims;
      ninputs = ndims - 1;
      loc = new double[ndims];
      Clear();
    }

    public double getloc(int dim) {
      return loc[dim];
    }

    public void setloc(int dim, double value) {
      loc[dim] = value;
    }

    public void Add(PointNd other) {
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] += other.loc[cnt];
      }
    }

    public void Subtract(PointNd other) {
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] -= other.loc[cnt];
      }
    }

    public void Copy_From(PointNd other) {
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] = other.loc[cnt];
      }
    }

    public void Multiply(double factor) {
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] *= factor;
      }
    }

    public double Magnitude(int dimensions) {
      double sumsq = 0.0;
      for (int cnt = 0; cnt < dimensions; cnt++) {
        sumsq += loc[cnt] * loc[cnt];
      }
      return Math.sqrt(sumsq);
    }
    PointNd another;

    public double Get_Distance(PointNd other, int dimensions) {
      another = other;
      double delta, dist = 0.0;// pythagorean distance
      for (int cnt = 0; cnt < dimensions; cnt++) {
        delta = other.loc[cnt] - this.loc[cnt];
        dist += delta * delta;// sum of the squares
      }
      dist = Math.sqrt(dist);
      return dist;
    }
    //--------------------------------------------------------------------

    public double Magnitude_Squared(int dimensions) {
      double sumsq = 0.0;
      for (int cnt = 0; cnt < dimensions; cnt++) {
        sumsq += loc[cnt] * loc[cnt];
      }
      return sumsq;
    }/* Magnitude_Squared */


    public void Get_Delta(PointNd other, int dimensions, PointNd pdelta) {
      pdelta.Clear();
      double delta;
      for (int cnt = 0; cnt < dimensions; cnt++) {
        delta = other.loc[cnt] - this.loc[cnt];
        pdelta.loc[cnt] = delta;
      }
    }

    public double Dot_Product(PointNd other) {
      double retval = 0.0;// assume other is a unit vector.
      for (int cnt = 0; cnt < ndims; cnt++) {
        if ((this.loc[cnt] != 0.0) && (other.loc[cnt] != 0.0)) {// Zero always wins, even against infinity in this usage.
          retval += this.loc[cnt] * other.loc[cnt];
        }
      }
      return retval;
    }

    public void Unitize() { // convert to unit vector
      double length = 0.0;
      for (int cnt = 0; cnt < ndims; cnt++) {
        double axis = this.loc[cnt];
        length += axis * axis;
      }
      length = Math.sqrt(length);//pythagoran length
      if (length == 0.0) {// fudge to avoid divide-by-zero
        length = java.lang.Double.MIN_VALUE;
      }
      for (int cnt = 0; cnt < ndims; cnt++) {
        this.loc[cnt] /= length;
      }
    }

    public void Randomize(double minval, double maxval) {
      double range = maxval - minval;
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] = minval + (Logic.wheel.nextDouble() * range);
      }
    }

    public void Jitter(double minval, double maxval) {
      double range = maxval - minval;
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] += minval + (Logic.wheel.nextDouble() * range);
      }
    }

    public void Clear() {
      for (int cnt = 0; cnt < ndims; cnt++) {
        loc[cnt] = 0.0;
      }
    }

    public void CheckNAN() {
      for (int cnt = 0; cnt < ndims; cnt++) {
        if (loc[cnt] != loc[cnt]) {
          boolean noop = true;
        }
      }
    }

    public void CheckInf() {
      for (int cnt = 0; cnt < ndims; cnt++) {
        if (java.lang.Double.isInfinite(loc[cnt])) {
          boolean noop = true;
        }
      }
    }
    /* *************************************************************************************************** */

    public boolean CheckVert() {
      boolean flat = true;
      for (int dcnt = 0; dcnt < ninputs; dcnt++) {
        flat &= (this.loc[dcnt] == 0.0);
      }
      //flat &= (pnt.loc[ninputs] != 0.0);
      if (flat) {
        boolean noop = true;
      }
      for (int cnt = 0; cnt < ndims; cnt++) {
        if (this.loc[cnt] == 1.0) {// orthogonality test
          boolean noop = true;
        }
      }
      return flat;
    }
    /* *************************************************************************************************** */

    public void Get_Cross_Product(PointNd a, PointNd b) {
      this.Clear();
      this.loc[0] = (a.loc[1] * b.loc[2] - a.loc[2] * b.loc[1]);
      this.loc[1] = (a.loc[2] * b.loc[0] - a.loc[0] * b.loc[2]);
      this.loc[2] = (a.loc[0] * b.loc[1] - a.loc[1] * b.loc[0]);
    }
    /* *************************************************************************************************** */

    public void Normal_To_Plane(PointNd plane) {// take the normal, and get the formula of the plane (x y z), with respect to z (or last dimension)
      double height = this.loc[ninputs];
      if (height == 0.0) {
        height = java.lang.Double.MIN_VALUE;
      }
      for (int dimcnt = 0; dimcnt < ninputs; dimcnt++) {
        plane.loc[dimcnt] = (-this.loc[dimcnt] / height);// multiply each axis length by the slope for that axis
      }
    }
    /* *************************************************************************************************** */

    public void Get_Steepest(PointNd steep) {// get the steepest line on a plane with respect to Z by rotating its normal 90 degrees.
      steep.Clear();
      double vertical = this.loc[ninputs]; // last dimension is the output 'Z' dim.
      double floorpotenuse = 0.0;// first get the floorpotenuse
      for (int cnt = 0; cnt < ninputs; cnt++) {
        double axis = this.loc[cnt];
        floorpotenuse += axis * axis;
      }
      floorpotenuse = Math.sqrt(floorpotenuse);
      steep.loc[ninputs] = floorpotenuse;// the result 'Z' value is the normal's floorpotenuse
      for (int cnt = 0; cnt < ninputs; cnt++) {
        double axis = this.loc[cnt];
        double ratio = (axis / floorpotenuse);
        steep.loc[cnt] = -vertical * ratio;// will always point up if normal points up.
      }
    }
  }
  /* *************************************************************************************************** */

  public class PlaneNd extends PointNd {
    /* *************************************************************************************************** */

    public PlaneNd(int num_dims) {
      super(num_dims);
    }
    /* *************************************************************************************************** */

    public double Get_Height(PointNd pnt) {
      // get the height of this plane, at this point's coordinates
      double plane_hgt = this.loc[ninputs];// last dimension holds height offset
      double height = 0.0;
      for (int dim = 0; dim < ninputs; dim++) {
        height += (pnt.loc[dim] * this.loc[dim]);// multiply each axis length by the slope for that axis
      }
      height += plane_hgt;// add in the base offset
      return height;
    }
    /* *************************************************************************************************** */

    public void Plane_Ramp_To_Normal(PointNd norm) {// take the normal, and get the formula of the plane (x y z), with respect to z (or last dimension)
      for (int dimcnt = 0; dimcnt < ninputs; dimcnt++) {
        norm.loc[dimcnt] = (-this.loc[dimcnt]);
      }
      norm.loc[ninputs] = 1.0;// should return a normal above the plane
      norm.Unitize();
    }
  }
  /* *************************************************************************************************** */

  public static class CPoint extends PointNd {/* Control Point */
    /* *************************************************************************************************** */

    public CPoint(int num_dims) {
      super(num_dims);
    }
  }
  /* *************************************************************************************************** */

  public class PointNd_List extends ArrayList<PointNd> {
    /* test comment */
    public PointNd_List(int newsize) {
      PointNd avg = new PointNd(ndims_init);
      for (int cnt = 0; cnt < newsize; cnt++) {
        PointNd pnt = new PointNd(ndims_init);
        pnt.Randomize(-1.0, 1.0);
        this.add(pnt);
      }
      avg.Multiply(newsize);// experiment to move all the points to the centroid
    }
    /* *************************************************************************************************** */

    public void Get_Average(PointNd ret_avg) {
      ret_avg.Clear();
      for (PointNd pnt : this) {
        ret_avg.Add(pnt);
      }
      ret_avg.Multiply(1.0 / (double) this.size());
    }
    /* *************************************************************************************************** */

    public void CheckNAN() {
      for (PointNd pnt : this) {
        pnt.CheckNAN();
      }
    }
  }/* PointNd_List */
  /* *************************************************************************************************** */
  public static class NodeBox {
  }
  /* *************************************************************************************************** */

  public static class Network {
  }
  /* *************************************************************************************************** */

  public static class Layers {
  }
}

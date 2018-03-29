package br.ol.math;

/**
 * Mat4 class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Mat4 {

    public double m00, m01, m02, m03;
    public double m10, m11, m12, m13;
    public double m20, m21, m22, m23;
    public double m30, m31, m32, m33;

    public Mat4() {
    }

    public void set(Mat4 m) {
        m00 = m.m00;
        m01 = m.m01;
        m02 = m.m02;
        m03 = m.m03;
        m10 = m.m10;
        m11 = m.m11;
        m12 = m.m12;
        m13 = m.m13;
        m20 = m.m20;
        m21 = m.m21;
        m22 = m.m22;
        m23 = m.m23;
        m30 = m.m30;
        m31 = m.m31;
        m32 = m.m32;
        m33 = m.m33;
    }

    public void setIdentity() {
        m01 = m02 = m03 = 0;
        m10 = m12 = m13 = 0;
        m20 = m21 = m23 = 0;
        m30 = m31 = m32 = 0;
        m00 = m11 = m22 = m33 = 1;
    }

    public void setPerspectiveProjection(double fov, double screenWidth) {
        double d = (screenWidth * 0.5) / Math.tan(fov * 0.5);
        setIdentity();
        m32 = -1 / d;
        m33 = 0;
    }

    public void setScale(double sx, double sy, double sz) {
        setIdentity();
        m00 = sx;
        m11 = sy;
        m22 = sz;
        m33 = 1;
    }

    public void setTranslation(double x, double y, double z) {
        setIdentity();
        m03 = x;
        m13 = y;
        m23 = z;
    }

    public void setTranslation(Vec4 v) {
        setIdentity();
        m03 = v.x;
        m13 = v.y;
        m23 = v.z;
    }

    public void setRotationX(double angle) {
        setIdentity();
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        m11 = c;
        m12 = -s;
        m21 = s;
        m22 = c;
    }

    public void setRotationY(double angle) {
        setIdentity();
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        m00 = c;
        m02 = s;
        m20 = -s;
        m22 = c;
    }

    public void setRotationZ(double angle) {
        setIdentity();
        double s = Math.sin(angle);
        double c = Math.cos(angle);
        m00 = c;
        m01 = -s;
        m10 = s;
        m11 = c;
    }

    public void multiply(Mat4 m) {
        double nm00 = m00 * m.m00 + m01 * m.m10 + m02 * m.m20 + m03 * m.m30;
        double nm01 = m00 * m.m01 + m01 * m.m11 + m02 * m.m21 + m03 * m.m31;
        double nm02 = m00 * m.m02 + m01 * m.m12 + m02 * m.m22 + m03 * m.m32;
        double nm03 = m00 * m.m03 + m01 * m.m13 + m02 * m.m23 + m03 * m.m33;
        double nm10 = m10 * m.m00 + m11 * m.m10 + m12 * m.m20 + m13 * m.m30;
        double nm11 = m10 * m.m01 + m11 * m.m11 + m12 * m.m21 + m13 * m.m31;
        double nm12 = m10 * m.m02 + m11 * m.m12 + m12 * m.m22 + m13 * m.m32;
        double nm13 = m10 * m.m03 + m11 * m.m13 + m12 * m.m23 + m13 * m.m33;
        double nm20 = m20 * m.m00 + m21 * m.m10 + m22 * m.m20 + m23 * m.m30;
        double nm21 = m20 * m.m01 + m21 * m.m11 + m22 * m.m21 + m23 * m.m31;
        double nm22 = m20 * m.m02 + m21 * m.m12 + m22 * m.m22 + m23 * m.m32;
        double nm23 = m20 * m.m03 + m21 * m.m13 + m22 * m.m23 + m23 * m.m33;
        double nm30 = m30 * m.m00 + m31 * m.m10 + m32 * m.m20 + m33 * m.m30;
        double nm31 = m30 * m.m01 + m31 * m.m11 + m32 * m.m21 + m33 * m.m31;
        double nm32 = m30 * m.m02 + m31 * m.m12 + m32 * m.m22 + m33 * m.m32;
        double nm33 = m30 * m.m03 + m31 * m.m13 + m32 * m.m23 + m33 * m.m33;
        m00 = nm00;
        m01 = nm01;
        m02 = nm02;
        m03 = nm03;
        m10 = nm10;
        m11 = nm11;
        m12 = nm12;
        m13 = nm13;
        m20 = nm20;
        m21 = nm21;
        m22 = nm22;
        m23 = nm23;
        m30 = nm30;
        m31 = nm31;
        m32 = nm32;
        m33 = nm33;
    }

    public void multiply(Mat4 m, Mat4 r) {
        double nm00 = m00 * m.m00 + m01 * m.m10 + m02 * m.m20 + m03 * m.m30;
        double nm01 = m00 * m.m01 + m01 * m.m11 + m02 * m.m21 + m03 * m.m31;
        double nm02 = m00 * m.m02 + m01 * m.m12 + m02 * m.m22 + m03 * m.m32;
        double nm03 = m00 * m.m03 + m01 * m.m13 + m02 * m.m23 + m03 * m.m33;
        double nm10 = m10 * m.m00 + m11 * m.m10 + m12 * m.m20 + m13 * m.m30;
        double nm11 = m10 * m.m01 + m11 * m.m11 + m12 * m.m21 + m13 * m.m31;
        double nm12 = m10 * m.m02 + m11 * m.m12 + m12 * m.m22 + m13 * m.m32;
        double nm13 = m10 * m.m03 + m11 * m.m13 + m12 * m.m23 + m13 * m.m33;
        double nm20 = m20 * m.m00 + m21 * m.m10 + m22 * m.m20 + m23 * m.m30;
        double nm21 = m20 * m.m01 + m21 * m.m11 + m22 * m.m21 + m23 * m.m31;
        double nm22 = m20 * m.m02 + m21 * m.m12 + m22 * m.m22 + m23 * m.m32;
        double nm23 = m20 * m.m03 + m21 * m.m13 + m22 * m.m23 + m23 * m.m33;
        double nm30 = m30 * m.m00 + m31 * m.m10 + m32 * m.m20 + m33 * m.m30;
        double nm31 = m30 * m.m01 + m31 * m.m11 + m32 * m.m21 + m33 * m.m31;
        double nm32 = m30 * m.m02 + m31 * m.m12 + m32 * m.m22 + m33 * m.m32;
        double nm33 = m30 * m.m03 + m31 * m.m13 + m32 * m.m23 + m33 * m.m33;
        r.m00 = nm00;
        r.m01 = nm01;
        r.m02 = nm02;
        r.m03 = nm03;
        r.m10 = nm10;
        r.m11 = nm11;
        r.m12 = nm12;
        r.m13 = nm13;
        r.m20 = nm20;
        r.m21 = nm21;
        r.m22 = nm22;
        r.m23 = nm23;
        r.m30 = nm30;
        r.m31 = nm31;
        r.m32 = nm32;
        r.m33 = nm33;
    }

    public void multiply(Vec4 v) {
        double nx = m00 * v.x + m01 * v.y + m02 * v.z + m03 * v.w;
        double ny = m10 * v.x + m11 * v.y + m12 * v.z + m13 * v.w;
        double nz = m20 * v.x + m21 * v.y + m22 * v.z + m23 * v.w;
        double nw = m30 * v.x + m31 * v.y + m32 * v.z + m33 * v.w;
        v.x = nx;
        v.y = ny;
        v.z = nz;
        v.w = nw;
    }

    public void multiply(Vec4 v, Vec4 r) {
        double nx = m00 * v.x + m01 * v.y + m02 * v.z + m03 * v.w;
        double ny = m10 * v.x + m11 * v.y + m12 * v.z + m13 * v.w;
        double nz = m20 * v.x + m21 * v.y + m22 * v.z + m23 * v.w;
        double nw = m30 * v.x + m31 * v.y + m32 * v.z + m33 * v.w;
        r.x = nx;
        r.y = ny;
        r.z = nz;
        r.w = nw;
    }
    
    public void scale(double s) {
        m00 *= s; m01 *= s; m02 *= s; m03 *= s;
        m10 *= s; m11 *= s; m12 *= s; m13 *= s;
        m20 *= s; m21 *= s; m22 *= s; m23 *= s;
        m30 *= s; m31 *= s; m32 *= s; m33 *= s;
    }
    
    // http://www.cg.info.hiroshima-cu.ac.jp/~miyazaki/knowledge/teche23.html
    // http://www.euclideanspace.com/maths/algebra/matrix/functions/inverse/fourD/index.htm
    public void invert() {
        double determinant = getDeterminant();
        if (determinant == 0) {
            //throw new RuntimeException("Matrix is not invertible !");
            return;
        }
        double nm00 = m12*m23*m31 - m13*m22*m31 + m13*m21*m32 - m11*m23*m32 - m12*m21*m33 + m11*m22*m33;
        double nm01 = m03*m22*m31 - m02*m23*m31 - m03*m21*m32 + m01*m23*m32 + m02*m21*m33 - m01*m22*m33;
        double nm02 = m02*m13*m31 - m03*m12*m31 + m03*m11*m32 - m01*m13*m32 - m02*m11*m33 + m01*m12*m33;
        double nm03 = m03*m12*m21 - m02*m13*m21 - m03*m11*m22 + m01*m13*m22 + m02*m11*m23 - m01*m12*m23;
        double nm10 = m13*m22*m30 - m12*m23*m30 - m13*m20*m32 + m10*m23*m32 + m12*m20*m33 - m10*m22*m33;
        double nm11 = m02*m23*m30 - m03*m22*m30 + m03*m20*m32 - m00*m23*m32 - m02*m20*m33 + m00*m22*m33;
        double nm12 = m03*m12*m30 - m02*m13*m30 - m03*m10*m32 + m00*m13*m32 + m02*m10*m33 - m00*m12*m33;
        double nm13 = m02*m13*m20 - m03*m12*m20 + m03*m10*m22 - m00*m13*m22 - m02*m10*m23 + m00*m12*m23;
        double nm20 = m11*m23*m30 - m13*m21*m30 + m13*m20*m31 - m10*m23*m31 - m11*m20*m33 + m10*m21*m33;
        double nm21 = m03*m21*m30 - m01*m23*m30 - m03*m20*m31 + m00*m23*m31 + m01*m20*m33 - m00*m21*m33;
        double nm22 = m01*m13*m30 - m03*m11*m30 + m03*m10*m31 - m00*m13*m31 - m01*m10*m33 + m00*m11*m33;
        double nm23 = m03*m11*m20 - m01*m13*m20 - m03*m10*m21 + m00*m13*m21 + m01*m10*m23 - m00*m11*m23;
        double nm30 = m12*m21*m30 - m11*m22*m30 - m12*m20*m31 + m10*m22*m31 + m11*m20*m32 - m10*m21*m32;
        double nm31 = m01*m22*m30 - m02*m21*m30 + m02*m20*m31 - m00*m22*m31 - m01*m20*m32 + m00*m21*m32;
        double nm32 = m02*m11*m30 - m01*m12*m30 - m02*m10*m31 + m00*m12*m31 + m01*m10*m32 - m00*m11*m32;
        double nm33 = m01*m12*m20 - m02*m11*m20 + m02*m10*m21 - m00*m12*m21 - m01*m10*m22 + m00*m11*m22;
        m00 = nm00; m01 = nm01; m02 = nm02; m03 = nm03;
        m10 = nm10; m11 = nm11; m12 = nm12; m13 = nm13;
        m20 = nm20; m21 = nm21; m22 = nm22; m23 = nm23;
        m30 = nm30; m31 = nm31; m32 = nm32; m33 = nm33;
        scale(1 / determinant);
    }

    public double getDeterminant() {
        return m03 * m12 * m21 * m30 - m02 * m13 * m21 * m30 - m03 * m11 * m22 * m30 + m01 * m13 * m22 * m30
             + m02 * m11 * m23 * m30 - m01 * m12 * m23 * m30 - m03 * m12 * m20 * m31 + m02 * m13 * m20 * m31
             + m03 * m10 * m22 * m31 - m00 * m13 * m22 * m31 - m02 * m10 * m23 * m31 + m00 * m12 * m23 * m31
             + m03 * m11 * m20 * m32 - m01 * m13 * m20 * m32 - m03 * m10 * m21 * m32 + m00 * m13 * m21 * m32
             + m01 * m10 * m23 * m32 - m00 * m11 * m23 * m32 - m02 * m11 * m20 * m33 + m01 * m12 * m20 * m33
             + m02 * m10 * m21 * m33 - m00 * m12 * m21 * m33 - m01 * m10 * m22 * m33 + m00 * m11 * m22 * m33;
    }
    
}

package com.opalfire.foodorder.helper;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class DataParser {
    public java.util.List<java.util.List<java.util.HashMap<java.lang.String, java.lang.String>>> parse(org.json.JSONObject r15) {
        /* JADX: method processing error */
/*
Error: java.lang.NullPointerException
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:75)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
	at jadx.api.JadxDecompiler$$Lambda$8/193388045.run(Unknown Source)
*/
        /*
        r14 = this;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r1 = "routes";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r15 = r15.getJSONArray(r1);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r1 = 0;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r2 = 0;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x000d:
        r3 = r15.length();	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        if (r2 >= r3) goto L_0x009d;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x0013:
        r3 = r15.get(r2);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r3 = (org.json.JSONObject) r3;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r4 = "legs";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r3 = r3.getJSONArray(r4);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r4 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r4.<init>();	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r5 = 0;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x0025:
        r6 = r3.length();	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        if (r5 >= r6) goto L_0x0095;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x002b:
        r6 = r3.get(r5);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r6 = (org.json.JSONObject) r6;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r7 = "steps";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r6 = r6.getJSONArray(r7);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r7 = 0;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x0038:
        r8 = r6.length();	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        if (r7 >= r8) goto L_0x008f;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x003e:
        r8 = r6.get(r7);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r8 = (org.json.JSONObject) r8;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r9 = "polyline";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r8 = r8.get(r9);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r8 = (org.json.JSONObject) r8;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r9 = "points";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r8 = r8.get(r9);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r8 = (java.lang.String) r8;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r8 = r14.decodePoly(r8);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r9 = 0;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x0059:
        r10 = r8.size();	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        if (r9 >= r10) goto L_0x008c;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x005f:
        r10 = new java.util.HashMap;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r10.<init>();	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r11 = "lat";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = r8.get(r9);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = (com.google.android.gms.maps.model.LatLng) r12;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = r12.latitude;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = java.lang.Double.toString(r12);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r10.put(r11, r12);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r11 = "lng";	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = r8.get(r9);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = (com.google.android.gms.maps.model.LatLng) r12;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = r12.longitude;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r12 = java.lang.Double.toString(r12);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r10.put(r11, r12);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r4.add(r10);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r9 = r9 + 1;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        goto L_0x0059;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x008c:
        r7 = r7 + 1;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        goto L_0x0038;	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
    L_0x008f:
        r0.add(r4);	 Catch:{ JSONException -> 0x0099, Exception -> 0x009d }
        r5 = r5 + 1;
        goto L_0x0025;
    L_0x0095:
        r2 = r2 + 1;
        goto L_0x000d;
    L_0x0099:
        r15 = move-exception;
        r15.printStackTrace();
    L_0x009d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.entriver.foodorder.helper.DataParser.parse(org.json.JSONObject):java.util.List<java.util.List<java.util.HashMap<java.lang.String, java.lang.String>>>");
    }

    private List<LatLng> decodePoly(String str) {
        List<LatLng> arrayList = new ArrayList();
        int length = str.length();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < length) {
            int i4 = 0;
            int i5 = 0;
            while (true) {
                int i6 = i + 1;
                i = str.charAt(i) - 63;
                i4 |= (i & 31) << i5;
                i5 += 5;
                if (i < 32) {
                    break;
                }
                i = i6;
            }
            i = ((i4 & 1) != 0 ? (i4 >> 1) ^ -1 : i4 >> 1) + i2;
            i2 = 0;
            i4 = 0;
            while (true) {
                i5 = i6 + 1;
                i6 = str.charAt(i6) - 63;
                i2 |= (i6 & 31) << i4;
                i4 += 5;
                if (i6 < 32) {
                    break;
                }
                i6 = i5;
            }
            i3 += (i2 & 1) != 0 ? (i2 >> 1) ^ -1 : i2 >> 1;
            double d = (double) i;
            Double.isNaN(d);
            d /= 100000.0d;
            double d2 = (double) i3;
            Double.isNaN(d2);
            arrayList.add(new LatLng(d, d2 / 100000.0d));
            i2 = i;
            i = i5;
        }
        return arrayList;
    }
}

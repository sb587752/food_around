package com.opalfire.orderaround.CountryPicker;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.zxing.client.result.ExpandedProductParsedResult;
import com.opalfire.orderaround.R;
import com.pubnub.api.builder.PubNubErrorBuilder;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class Country {
    public static final Country[] COUNTRIES;
    private static List<Country> allCountriesList;

    static {
        Country[] countryArr = new Country[Callback.DEFAULT_SWIPE_ANIMATION_DURATION];
        countryArr[0] = new Country("AD", "Andorra", "+376", R.drawable.flag_ad);
        countryArr[1] = new Country("AE", "United Arab Emirates", "+971", R.drawable.flag_ae);
        countryArr[2] = new Country("AF", "Afghanistan", "+93", R.drawable.flag_af);
        countryArr[3] = new Country("AG", "Antigua and Barbuda", "+1", R.drawable.flag_ag);
        countryArr[4] = new Country("AI", "Anguilla", "+1", R.drawable.flag_ai);
        countryArr[5] = new Country("AL", "Albania", "+355", R.drawable.flag_al);
        countryArr[6] = new Country("AM", "Armenia", "+374", R.drawable.flag_am);
        countryArr[7] = new Country("AO", "Angola", "+244", R.drawable.flag_ao);
        countryArr[8] = new Country("AQ", "Antarctica", "+672", R.drawable.flag_aq);
        countryArr[9] = new Country("AR", "Argentina", "+54", R.drawable.flag_ar);
        countryArr[10] = new Country("AS", "AmericanSamoa", "+1", R.drawable.flag_as);
        countryArr[11] = new Country("AT", "Austria", "+43", R.drawable.flag_at);
        countryArr[12] = new Country("AU", "Australia", "+61", R.drawable.flag_au);
        countryArr[13] = new Country("AW", "Aruba", "+297", R.drawable.flag_aw);
        countryArr[14] = new Country("AX", "Åland Islands", "+358", R.drawable.flag_ax);
        countryArr[15] = new Country("AZ", "Azerbaijan", "+994", R.drawable.flag_az);
        countryArr[16] = new Country("BA", "Bosnia and Herzegovina", "+387", R.drawable.flag_ba);
        countryArr[17] = new Country("BB", "Barbados", "+1", R.drawable.flag_bb);
        countryArr[18] = new Country("BD", "Bangladesh", "+880", R.drawable.flag_bd);
        countryArr[19] = new Country("BE", "Belgium", "+32", R.drawable.flag_be);
        countryArr[20] = new Country("BF", "Burkina Faso", "+226", R.drawable.flag_bf);
        countryArr[21] = new Country("BG", "Bulgaria", "+359", R.drawable.flag_bg);
        countryArr[22] = new Country("BH", "Bahrain", "+973", R.drawable.flag_bh);
        countryArr[23] = new Country("BI", "Burundi", "+257", R.drawable.flag_bi);
        countryArr[24] = new Country("BJ", "Benin", "+229", R.drawable.flag_bj);
        countryArr[25] = new Country("BL", "Saint Barthélemy", "+590", R.drawable.flag_bl);
        countryArr[26] = new Country("BM", "Bermuda", "+1", R.drawable.flag_bm);
        countryArr[27] = new Country("BN", "Brunei Darussalam", "+673", R.drawable.flag_bn);
        countryArr[28] = new Country("BO", "Bolivia, Plurinational State of", "+591", R.drawable.flag_bo);
        countryArr[29] = new Country("BQ", "Bonaire", "+599", R.drawable.flag_bq);
        countryArr[30] = new Country("BR", "Brazil", "+55", R.drawable.flag_br);
        countryArr[31] = new Country("BS", "Bahamas", "+1", R.drawable.flag_bs);
        countryArr[32] = new Country("BT", "Bhutan", "+975", R.drawable.flag_bt);
        countryArr[33] = new Country("BV", "Bouvet Island", "+47", R.drawable.flag_bv);
        countryArr[34] = new Country("BW", "Botswana", "+267", R.drawable.flag_bw);
        countryArr[35] = new Country("BY", "Belarus", "+375", R.drawable.flag_by);
        countryArr[36] = new Country("BZ", "Belize", "+501", R.drawable.flag_bz);
        countryArr[37] = new Country("CA", "Canada", "+1", R.drawable.flag_ca);
        countryArr[38] = new Country("CC", "Cocos (Keeling) Islands", "+61", R.drawable.flag_cc);
        countryArr[39] = new Country("CD", "Congo, The Democratic Republic of the", "+243", R.drawable.flag_cd);
        countryArr[40] = new Country("CF", "Central African Republic", "+236", R.drawable.flag_cf);
        countryArr[41] = new Country("CG", "Congo", "+242", R.drawable.flag_cg);
        countryArr[42] = new Country("CH", "Switzerland", "+41", R.drawable.flag_ch);
        countryArr[43] = new Country("CI", "Ivory Coast", "+225", R.drawable.flag_ci);
        countryArr[44] = new Country("CK", "Cook Islands", "+682", R.drawable.flag_ck);
        countryArr[45] = new Country("CL", "Chile", "+56", R.drawable.flag_cl);
        countryArr[46] = new Country("CM", "Cameroon", "+237", R.drawable.flag_cm);
        countryArr[47] = new Country("CN", "China", "+86", R.drawable.flag_cn);
        countryArr[48] = new Country("CO", "Colombia", "+57", R.drawable.flag_co);
        countryArr[49] = new Country("CR", "Costa Rica", "+506", R.drawable.flag_cr);
        countryArr[50] = new Country("CU", "Cuba", "+53", R.drawable.flag_cu);
        countryArr[51] = new Country("CV", "Cape Verde", "+238", R.drawable.flag_cv);
        countryArr[52] = new Country("CW", "Curacao", "+599", R.drawable.flag_cw);
        countryArr[53] = new Country("CX", "Christmas Island", "+61", R.drawable.flag_cx);
        countryArr[54] = new Country("CY", "Cyprus", "+357", R.drawable.flag_cy);
        countryArr[55] = new Country("CZ", "Czech Republic", "+420", R.drawable.flag_cz);
        countryArr[56] = new Country("DE", "Germany", "+49", R.drawable.flag_de);
        countryArr[57] = new Country("DJ", "Djibouti", "+253", R.drawable.flag_dj);
        countryArr[58] = new Country("DK", "Denmark", "+45", R.drawable.flag_dk);
        countryArr[59] = new Country("DM", "Dominica", "+1", R.drawable.flag_dm);
        countryArr[60] = new Country("DO", "Dominican Republic", "+1", R.drawable.flag_do);
        countryArr[61] = new Country("DZ", "Algeria", "+213", R.drawable.flag_dz);
        countryArr[62] = new Country("EC", "Ecuador", "+593", R.drawable.flag_ec);
        countryArr[63] = new Country("EE", "Estonia", "+372", R.drawable.flag_ee);
        countryArr[64] = new Country("EG", "Egypt", "+20", R.drawable.flag_eg);
        countryArr[65] = new Country("EH", "Western Sahara", "+212", R.drawable.flag_eh);
        countryArr[66] = new Country("ER", "Eritrea", "+291", R.drawable.flag_er);
        countryArr[67] = new Country("ES", "Spain", "+34", R.drawable.flag_es);
        countryArr[68] = new Country("ET", "Ethiopia", "+251", R.drawable.flag_et);
        countryArr[69] = new Country("FI", "Finland", "+358", R.drawable.flag_fi);
        countryArr[70] = new Country("FJ", "Fiji", "+679", R.drawable.flag_fj);
        countryArr[71] = new Country("FK", "Falkland Islands (Malvinas)", "+500", R.drawable.flag_fk);
        countryArr[72] = new Country("FM", "Micronesia, Federated States of", "+691", R.drawable.flag_fm);
        countryArr[73] = new Country("FO", "Faroe Islands", "+298", R.drawable.flag_fo);
        countryArr[74] = new Country("FR", "France", "+33", R.drawable.flag_fr);
        countryArr[75] = new Country("GA", "Gabon", "+241", R.drawable.flag_ga);
        countryArr[76] = new Country("GB", "United Kingdom", "+44", R.drawable.flag_gb);
        countryArr[77] = new Country("GD", "Grenada", "+1", R.drawable.flag_gd);
        countryArr[78] = new Country("GE", "Georgia", "+995", R.drawable.flag_ge);
        countryArr[79] = new Country("GF", "French Guiana", "+594", R.drawable.flag_gf);
        countryArr[80] = new Country("GG", "Guernsey", "+44", R.drawable.flag_gg);
        countryArr[81] = new Country("GH", "Ghana", "+233", R.drawable.flag_gh);
        countryArr[82] = new Country("GI", "Gibraltar", "+350", R.drawable.flag_gi);
        countryArr[83] = new Country("GL", "Greenland", "+299", R.drawable.flag_gl);
        countryArr[84] = new Country("GM", "Gambia", "+220", R.drawable.flag_gm);
        countryArr[85] = new Country("GN", "Guinea", "+224", R.drawable.flag_gn);
        countryArr[86] = new Country("GP", "Guadeloupe", "+590", R.drawable.flag_gp);
        countryArr[87] = new Country("GQ", "Equatorial Guinea", "+240", R.drawable.flag_gq);
        countryArr[88] = new Country("GR", "Greece", "+30", R.drawable.flag_gr);
        countryArr[89] = new Country("GS", "South Georgia and the South Sandwich Islands", "+500", R.drawable.flag_gs);
        countryArr[90] = new Country("GT", "Guatemala", "+502", R.drawable.flag_gt);
        countryArr[91] = new Country("GU", "Guam", "+1", R.drawable.flag_gu);
        countryArr[92] = new Country("GW", "Guinea-Bissau", "+245", R.drawable.flag_gw);
        countryArr[93] = new Country("GY", "Guyana", "+595", R.drawable.flag_gy);
        countryArr[94] = new Country("HK", "Hong Kong", "+852", R.drawable.flag_hk);
        countryArr[95] = new Country("HM", "Heard Island and McDonald Islands", "", R.drawable.flag_hm);
        countryArr[96] = new Country("HN", "Honduras", "+504", R.drawable.flag_hn);
        countryArr[97] = new Country("HR", "Croatia", "+385", R.drawable.flag_hr);
        countryArr[98] = new Country("HT", "Haiti", "+509", R.drawable.flag_ht);
        countryArr[99] = new Country("HU", "Hungary", "+36", R.drawable.flag_hu);
        countryArr[100] = new Country("ID", "Indonesia", "+62", R.drawable.flag_id);
        countryArr[101] = new Country("IE", "Ireland", "+353", R.drawable.flag_ie);
        countryArr[102] = new Country("IL", "Israel", "+972", R.drawable.flag_il);
        countryArr[103] = new Country("IM", "Isle of Man", "+44", R.drawable.flag_im);
        countryArr[104] = new Country("IN", "India", "+91", R.drawable.flag_in);
        countryArr[105] = new Country("IO", "British Indian Ocean Territory", "+246", R.drawable.flag_io);
        countryArr[106] = new Country("IQ", "Iraq", "+964", R.drawable.flag_iq);
        countryArr[107] = new Country("IR", "Iran, Islamic Republic of", "+98", R.drawable.flag_ir);
        countryArr[108] = new Country("IS", "Iceland", "+354", R.drawable.flag_is);
        countryArr[109] = new Country("IT", "Italy", "+39", R.drawable.flag_it);
        countryArr[110] = new Country("JE", "Jersey", "+44", R.drawable.flag_je);
        countryArr[111] = new Country("JM", "Jamaica", "+1", R.drawable.flag_jm);
        countryArr[112] = new Country("JO", "Jordan", "+962", R.drawable.flag_jo);
        countryArr[113] = new Country("JP", "Japan", "+81", R.drawable.flag_jp);
        countryArr[114] = new Country("KE", "Kenya", "+254", R.drawable.flag_ke);
        countryArr[115] = new Country(ExpandedProductParsedResult.KILOGRAM, "Kyrgyzstan", "+996", R.drawable.flag_kg);
        countryArr[116] = new Country("KH", "Cambodia", "+855", R.drawable.flag_kh);
        countryArr[117] = new Country("KI", "Kiribati", "+686", R.drawable.flag_ki);
        countryArr[118] = new Country("KM", "Comoros", "+269", R.drawable.flag_km);
        countryArr[119] = new Country("KN", "Saint Kitts and Nevis", "+1", R.drawable.flag_kn);
        countryArr[120] = new Country("KP", "North Korea", "+850", R.drawable.flag_kp);
        countryArr[PubNubErrorBuilder.PNERR_JSON_ERROR] = new Country("KR", "South Korea", "+82", R.drawable.flag_kr);
        countryArr[PubNubErrorBuilder.PNERR_PROTOCOL_EXCEPTION] = new Country("KW", "Kuwait", "+965", R.drawable.flag_kw);
        countryArr[PubNubErrorBuilder.PNERR_READINPUT] = new Country("KY", "Cayman Islands", "+345", R.drawable.flag_ky);
        countryArr[PubNubErrorBuilder.PNERR_BAD_GATEWAY] = new Country("KZ", "Kazakhstan", "+7", R.drawable.flag_kz);
        countryArr[PubNubErrorBuilder.PNERR_INTERNAL_ERROR] = new Country("LA", "Lao People's Democratic Republic", "+856", R.drawable.flag_la);
        countryArr[PubNubErrorBuilder.PNERR_PARSING_ERROR] = new Country(ExpandedProductParsedResult.POUND, "Lebanon", "+961", R.drawable.flag_lb);
        countryArr[PubNubErrorBuilder.PNERR_BAD_REQUEST] = new Country("LC", "Saint Lucia", "+1", R.drawable.flag_lc);
        countryArr[128] = new Country("LI", "Liechtenstein", "+423", R.drawable.flag_li);
        countryArr[PubNubErrorBuilder.PNERR_NOT_FOUND] = new Country("LK", "Sri Lanka", "+94", R.drawable.flag_lk);
        countryArr[PubNubErrorBuilder.PNERR_HTTP_SUBSCRIBE_TIMEOUT] = new Country("LR", "Liberia", "+231", R.drawable.flag_lr);
        countryArr[PubNubErrorBuilder.PNERR_INVALID_ARGUMENTS] = new Country("LS", "Lesotho", "+266", R.drawable.flag_ls);
        countryArr[PubNubErrorBuilder.PNERR_CHANNEL_MISSING] = new Country("LT", "Lithuania", "+370", R.drawable.flag_lt);
        countryArr[PubNubErrorBuilder.PNERR_CONNECTION_NOT_SET] = new Country("LU", "Luxembourg", "+352", R.drawable.flag_lu);
        countryArr[PubNubErrorBuilder.PNERR_CHANNEL_GROUP_PARSING_ERROR] = new Country("LV", "Latvia", "+371", R.drawable.flag_lv);
        countryArr[PubNubErrorBuilder.PNERR_CRYPTO_ERROR] = new Country("LY", "Libyan Arab Jamahiriya", "+218", R.drawable.flag_ly);
        countryArr[PubNubErrorBuilder.PNERR_GROUP_MISSING] = new Country("MA", "Morocco", "+212", R.drawable.flag_ma);
        countryArr[PubNubErrorBuilder.PNERR_AUTH_KEYS_MISSING] = new Country("MC", "Monaco", "+377", R.drawable.flag_mc);
        countryArr[PubNubErrorBuilder.PNERR_SUBSCRIBE_KEY_MISSING] = new Country("MD", "Moldova, Republic of", "+373", R.drawable.flag_md);
        countryArr[PubNubErrorBuilder.PNERR_PUBLISH_KEY_MISSING] = new Country("ME", "Montenegro", "+382", R.drawable.flag_me);
        countryArr[PubNubErrorBuilder.PNERR_STATE_MISSING] = new Country("MF", "Saint Martin", "+590", R.drawable.flag_mf);
        countryArr[PubNubErrorBuilder.PNERR_CHANNEL_AND_GROUP_MISSING] = new Country("MG", "Madagascar", "+261", R.drawable.flag_mg);
        countryArr[PubNubErrorBuilder.PNERR_MESSAGE_MISSING] = new Country("MH", "Marshall Islands", "+692", R.drawable.flag_mh);
        countryArr[PubNubErrorBuilder.PNERR_PUSH_TYPE_MISSING] = new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389", R.drawable.flag_mk);
        countryArr[PubNubErrorBuilder.PNERR_DEVICE_ID_MISSING] = new Country("ML", "Mali", "+223", R.drawable.flag_ml);
        countryArr[145] = new Country("MM", "Myanmar", "+95", R.drawable.flag_mm);
        countryArr[146] = new Country("MN", "Mongolia", "+976", R.drawable.flag_mn);
        countryArr[147] = new Country("MO", "Macao", "+853", R.drawable.flag_mo);
        countryArr[148] = new Country("MP", "Northern Mariana Islands", "+1", R.drawable.flag_mp);
        countryArr[149] = new Country("MQ", "Martinique", "+596", R.drawable.flag_mq);
        countryArr[150] = new Country("MR", "Mauritania", "+222", R.drawable.flag_mr);
        countryArr[151] = new Country("MS", "Montserrat", "+1", R.drawable.flag_ms);
        countryArr[152] = new Country("MT", "Malta", "+356", R.drawable.flag_mt);
        countryArr[153] = new Country("MU", "Mauritius", "+230", R.drawable.flag_mu);
        countryArr[154] = new Country("MV", "Maldives", "+960", R.drawable.flag_mv);
        countryArr[155] = new Country("MW", "Malawi", "+265", R.drawable.flag_mw);
        countryArr[156] = new Country("MX", "Mexico", "+52", R.drawable.flag_mx);
        countryArr[157] = new Country("MY", "Malaysia", "+60", R.drawable.flag_my);
        countryArr[158] = new Country("MZ", "Mozambique", "+258", R.drawable.flag_mz);
        countryArr[159] = new Country("NA", "Namibia", "+264", R.drawable.flag_na);
        countryArr[160] = new Country("NC", "New Caledonia", "+687", R.drawable.flag_nc);
        countryArr[161] = new Country("NE", "Niger", "+227", R.drawable.flag_ne);
        countryArr[162] = new Country("NF", "Norfolk Island", "+672", R.drawable.flag_nf);
        countryArr[163] = new Country("NG", "Nigeria", "+234", R.drawable.flag_ng);
        countryArr[164] = new Country("NI", "Nicaragua", "+505", R.drawable.flag_ni);
        countryArr[165] = new Country("NL", "Netherlands", "+31", R.drawable.flag_nl);
        countryArr[166] = new Country("NO", "Norway", "+47", R.drawable.flag_no);
        countryArr[167] = new Country("NP", "Nepal", "+977", R.drawable.flag_np);
        countryArr[168] = new Country("NR", "Nauru", "+674", R.drawable.flag_nr);
        countryArr[169] = new Country("NU", "Niue", "+683", R.drawable.flag_nu);
        countryArr[170] = new Country("NZ", "New Zealand", "+64", R.drawable.flag_nz);
        countryArr[171] = new Country("OM", "Oman", "+968", R.drawable.flag_om);
        countryArr[172] = new Country("PA", "Panama", "+507", R.drawable.flag_pa);
        countryArr[173] = new Country("PE", "Peru", "+51", R.drawable.flag_pe);
        countryArr[174] = new Country("PF", "French Polynesia", "+689", R.drawable.flag_pf);
        countryArr[175] = new Country("PG", "Papua New Guinea", "+675", R.drawable.flag_pg);
        countryArr[176] = new Country("PH", "Philippines", "+63", R.drawable.flag_ph);
        countryArr[177] = new Country("PK", "Pakistan", "+92", R.drawable.flag_pk);
        countryArr[178] = new Country("PL", "Poland", "+48", R.drawable.flag_pl);
        countryArr[179] = new Country("PM", "Saint Pierre and Miquelon", "+508", R.drawable.flag_pm);
        countryArr[180] = new Country("PN", "Pitcairn", "+872", R.drawable.flag_pn);
        countryArr[181] = new Country("PR", "Puerto Rico", "+1", R.drawable.flag_pr);
        countryArr[182] = new Country("PS", "Palestinian Territory, Occupied", "+970", R.drawable.flag_ps);
        countryArr[183] = new Country("PT", "Portugal", "+351", R.drawable.flag_pt);
        countryArr[184] = new Country("PW", "Palau", "+680", R.drawable.flag_pw);
        countryArr[185] = new Country("PY", "Paraguay", "+595", R.drawable.flag_py);
        countryArr[186] = new Country("QA", "Qatar", "+974", R.drawable.flag_qa);
        countryArr[187] = new Country("RE", "Réunion", "+262", R.drawable.flag_re);
        countryArr[188] = new Country("RO", "Romania", "+40", R.drawable.flag_ro);
        countryArr[189] = new Country("RS", "Serbia", "+381", R.drawable.flag_rs);
        countryArr[FacebookRequestErrorClassification.EC_INVALID_TOKEN] = new Country("RU", "Russia", "+7", R.drawable.flag_ru);
        countryArr[191] = new Country("RW", "Rwanda", "+250", R.drawable.flag_rw);
        countryArr[192] = new Country("SA", "Saudi Arabia", "+966", R.drawable.flag_sa);
        countryArr[193] = new Country("SB", "Solomon Islands", "+677", R.drawable.flag_sb);
        countryArr[194] = new Country("SC", "Seychelles", "+248", R.drawable.flag_sc);
        countryArr[195] = new Country("SD", "Sudan", "+249", R.drawable.flag_sd);
        countryArr[196] = new Country("SE", "Sweden", "+46", R.drawable.flag_se);
        countryArr[197] = new Country("SG", "Singapore", "+65", R.drawable.flag_sg);
        countryArr[198] = new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290", R.drawable.flag_sh);
        countryArr[199] = new Country("SI", "Slovenia", "+386", R.drawable.flag_si);
        countryArr[Callback.DEFAULT_DRAG_ANIMATION_DURATION] = new Country("SJ", "Svalbard and Jan Mayen", "+47", R.drawable.flag_sj);
        countryArr[201] = new Country("SK", "Slovakia", "+421", R.drawable.flag_sk);
        countryArr[202] = new Country("SL", "Sierra Leone", "+232", R.drawable.flag_sl);
        countryArr[203] = new Country("SM", "San Marino", "+378", R.drawable.flag_sm);
        countryArr[204] = new Country("SN", "Senegal", "+221", R.drawable.flag_sn);
        countryArr[205] = new Country("SO", "Somalia", "+252", R.drawable.flag_so);
        countryArr[206] = new Country("SR", "Suriname", "+597", R.drawable.flag_sr);
        countryArr[207] = new Country("SS", "South Sudan", "+211", R.drawable.flag_ss);
        countryArr[208] = new Country("ST", "Sao Tome and Principe", "+239", R.drawable.flag_st);
        countryArr[209] = new Country("SV", "El Salvador", "+503", R.drawable.flag_sv);
        countryArr[210] = new Country("SX", "Sint Maarten", "+1", R.drawable.flag_sx);
        countryArr[211] = new Country("SY", "Syrian Arab Republic", "+963", R.drawable.flag_sy);
        countryArr[212] = new Country("SZ", "Swaziland", "+268", R.drawable.flag_sz);
        countryArr[213] = new Country("TC", "Turks and Caicos Islands", "+1", R.drawable.flag_tc);
        countryArr[214] = new Country("TD", "Chad", "+235", R.drawable.flag_td);
        countryArr[215] = new Country("TF", "French Southern Territories", "+262", R.drawable.flag_tf);
        countryArr[216] = new Country("TG", "Togo", "+228", R.drawable.flag_tg);
        countryArr[217] = new Country("TH", "Thailand", "+66", R.drawable.flag_th);
        countryArr[218] = new Country("TJ", "Tajikistan", "+992", R.drawable.flag_tj);
        countryArr[219] = new Country("TK", "Tokelau", "+690", R.drawable.flag_tk);
        countryArr[220] = new Country("TL", "East Timor", "+670", R.drawable.flag_tl);
        countryArr[221] = new Country("TM", "Turkmenistan", "+993", R.drawable.flag_tm);
        countryArr[222] = new Country("TN", "Tunisia", "+216", R.drawable.flag_tn);
        countryArr[223] = new Country("TO", "Tonga", "+676", R.drawable.flag_to);
        countryArr[224] = new Country("TR", "Turkey", "+90", R.drawable.flag_tr);
        countryArr[225] = new Country("TT", "Trinidad and Tobago", "+1", R.drawable.flag_tt);
        countryArr[226] = new Country("TV", "Tuvalu", "+688", R.drawable.flag_tv);
        countryArr[227] = new Country("TW", "Taiwan", "+886", R.drawable.flag_tw);
        countryArr[228] = new Country("TZ", "Tanzania, United Republic of", "+255", R.drawable.flag_tz);
        countryArr[229] = new Country("UA", "Ukraine", "+380", R.drawable.flag_ua);
        countryArr[230] = new Country("UG", "Uganda", "+256", R.drawable.flag_ug);
        countryArr[231] = new Country("UM", "U.S. Minor Outlying Islands", "", R.drawable.flag_um);
        countryArr[232] = new Country("US", "United States", "+1", R.drawable.flag_us);
        countryArr[233] = new Country("UY", "Uruguay", "+598", R.drawable.flag_uy);
        countryArr[234] = new Country("UZ", "Uzbekistan", "+998", R.drawable.flag_uz);
        countryArr[235] = new Country("VA", "Holy See (Vatican City State)", "+379", R.drawable.flag_va);
        countryArr[236] = new Country("VC", "Saint Vincent and the Grenadines", "+1", R.drawable.flag_vc);
        countryArr[237] = new Country("VE", "Venezuela, Bolivarian Republic of", "+58", R.drawable.flag_ve);
        countryArr[238] = new Country("VG", "Virgin Islands, British", "+1", R.drawable.flag_vg);
        countryArr[239] = new Country("VI", "Virgin Islands, U.S.", "+1", R.drawable.flag_vi);
        countryArr[240] = new Country("VN", "Viet Nam", "+84", R.drawable.flag_vn);
        countryArr[241] = new Country("VU", "Vanuatu", "+678", R.drawable.flag_vu);
        countryArr[242] = new Country("WF", "Wallis and Futuna", "+681", R.drawable.flag_wf);
        countryArr[243] = new Country("WS", "Samoa", "+685", R.drawable.flag_ws);
        countryArr[244] = new Country("XK", "Kosovo", "+383", R.drawable.flag_xk);
        countryArr[245] = new Country("YE", "Yemen", "+967", R.drawable.flag_ye);
        countryArr[246] = new Country("YT", "Mayotte", "+262", R.drawable.flag_yt);
        countryArr[247] = new Country("ZA", "South Africa", "+27", R.drawable.flag_za);
        countryArr[248] = new Country("ZM", "Zambia", "+260", R.drawable.flag_zm);
        countryArr[249] = new Country("ZW", "Zimbabwe", "+263", R.drawable.flag_zw);
        COUNTRIES = countryArr;
    }

    private String code;
    private String dialCode;
    private int flag = -1;
    private String name;

    public Country(String str, String str2, String str3, int i) {
        this.code = str;
        this.name = str2;
        this.dialCode = str3;
        this.flag = i;
    }

    public static Country getCountryFromSIM(String str) {
        return null;
    }

    public static List<Country> getAllCountries() {
        if (allCountriesList == null) {
            allCountriesList = Arrays.asList(COUNTRIES);
        }
        return allCountriesList;
    }

    public static Country getCountryByISO(String str) {
        Country country = new Country("", "", "", -1);
        country.setCode(str.toUpperCase());
        int search = Arrays.binarySearch(COUNTRIES, country, new ISOCodeComparator());
        if (search < 0) {
            return null;
        }
        return COUNTRIES[search];
    }

    public static Country getCountryByName(String str) {
        for (Country country : COUNTRIES) {
            if (str.equals(country.getName())) {
                return country;
            }
        }
        return null;
    }

    public static Country getCountryByLocale(Locale locale) {
        return getCountryByISO(locale.getISO3Country().substring(0, 2).toLowerCase());
    }

    public static Country getCountryFromSIM(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimState() != 1 ? getCountryByISO(telephonyManager.getSimCountryIso()) : null;
    }

    public String getDialCode() {
        return this.dialCode;
    }

    public void setDialCode(String str) {
        this.dialCode = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
        if (TextUtils.isEmpty(this.name)) {
            this.name = new Locale("", str).getDisplayName();
        }
    }

    public String getName() {
        return this.name;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int i) {
        this.flag = i;
    }

    public void loadFlagByCode(Context context) {
        if (this.flag == -1) {
            try {
                Resources resources = context.getResources();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("flag_");
                stringBuilder.append(this.code.toLowerCase(Locale.ENGLISH));
                this.flag = resources.getIdentifier(stringBuilder.toString(), "drawable", context.getPackageName());
            } catch (Exception e) {
                e.printStackTrace();
                this.flag = -1;
            }
        }
    }

    public static class ISOCodeComparator implements Comparator<Country> {
        public int compare(Country country, Country country2) {
            return country.code.compareTo(country2.code);
        }
    }

    public static class NameComparator implements Comparator<Country> {
        public int compare(Country country, Country country2) {
            return country.name.compareTo(country2.name);
        }
    }
}

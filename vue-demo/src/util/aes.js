import CryptoJS from 'crypto-js'

// 需要和后端一致
const KEY = CryptoJS.enc.Utf8.parse('sie202207pg3ylbm');
const IV = CryptoJS.enc.Utf8.parse('sie202207pg3ylbm');

export default {

  /**
   * 加密
   * @param {*} word
   * @param {*} keyStr
   * @param {*} ivStr
   */
  encrypt (word, keyStr, ivStr) {
    let key = KEY;
    let iv = IV;
    if (keyStr) {
      key = CryptoJS.enc.Utf8.parse(keyStr);
      iv = CryptoJS.enc.Utf8.parse(ivStr);
    }
    let srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {
      iv: iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.ZeroPadding
    });
    return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
  },

  /**
   * 解密
   * @param {*} word
   * @param {*} keyStr
   * @param {*} ivStr
   */
  decrypt (word, keyStr, ivStr) {
    let key = KEY;
    let iv = IV;

    if (keyStr) {
      key = CryptoJS.enc.Utf8.parse(keyStr);
      iv = CryptoJS.enc.Utf8.parse(ivStr);
    }

    let base64 = CryptoJS.enc.Base64.parse(word);
    let src = CryptoJS.enc.Base64.stringify(base64);

    let decrypt = CryptoJS.AES.decrypt(src, key, {
      iv: iv,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.ZeroPadding
    });

    let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
    return decryptedStr.toString();
  }
}


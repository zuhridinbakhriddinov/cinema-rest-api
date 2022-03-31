package uz.pdp.appcinemarest;

import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.stripe.Stripe;
import com.stripe.model.Balance;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import uz.pdp.appcinemarest.service.TicketService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// Zuhridin Bakhriddinov 3/26/2022 12:39 AM
public class GenerateQRCode {
    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException
    {
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
    }
    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }
    @SneakyThrows
    public static void main(String args[]) throws WriterException, IOException, NotFoundException
    {
       /* String str= "THE HABIT OF PERSISTENCE IS THE HABIT OF VICTORY.";
        String path = "D:\\upload\\Quote.png";
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        generateQRcode(str, path, charset, hashMap, 200, 200);//increase or decrease height and width accodingly
        System.out.println("QR Code created successfully.");*/
        byte[] qrCodeImage = getQRCodeImage("1111111", 400, 400);
        int length = qrCodeImage.length;
        Customer customer = new Customer();
        customer.setEmail("leozukich@gmail.com");
  //      System.out.println(customer.getBalance());
        Stripe.apiKey = "sk_test_51KhfDrGNKbQ4R3wKLw6i1KUhcMkIpIxduTX2JOaooftmI9u3lxS8j4apN9kYJ9UZVRl9230Jn1kWBALtzysklSEx007WRYy1hA";
      //  System.out.println(Balance.retrieve());
        Charge charge=new Charge();
        charge.setId("ch_3KiDL9GNKbQ4R3wK0J4u6H7K");
        charge.setReceiptEmail("leozukich@gmail.com");
        System.out.println(charge.getCustomer());

    }



}

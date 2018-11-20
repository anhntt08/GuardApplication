package safeobject.guardapplication;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PopupResetPassword {
    Button btnOk, btnCancel;
    TextInputEditText txtcurrentpass, txtnewpass, txtconfirmpass;
    Dialog dialog;

    public PopupResetPassword(Context context) {
        dialog = new Dialog(context);

        dialog.setContentView(R.layout.popup_reset_password);

        btnOk = dialog.findViewById(R.id.setting_btnOk);
        btnCancel = dialog.findViewById(R.id.setting_btnCancel);
        txtnewpass = dialog.findViewById(R.id.setting_Newpassword);
        txtconfirmpass = dialog.findViewById(R.id.setting_Confirmpassword);
        txtcurrentpass = dialog.findViewById(R.id.setting_Currentpassword);

        final String pass;

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass = getText(txtnewpass);
                String confirmpass = getText(txtconfirmpass);
                String currentPass = getText(txtcurrentpass);

                updatePass(newpass);

                if(!confirmpass.equalsIgnoreCase(newpass)){
                    Toast.makeText(v.getContext(),"Password Not matching",Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private String getText(TextInputEditText textInputEditText){
        return textInputEditText.getText().toString();
    }

    private void updatePass(String pass){

    }
}

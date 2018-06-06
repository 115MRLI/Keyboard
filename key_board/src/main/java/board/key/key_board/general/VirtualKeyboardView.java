package board.key.key_board.general;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import board.key.key_board.R;
import board.key.key_board.adapter.KeyBoardAdapter;

/**
 * 虚拟键盘
 */
public class VirtualKeyboardView extends RelativeLayout implements View.OnClickListener {

    Context context;

    //因为就6个输入框不会变了，用数组内存申请固定空间，比List省空间（自己认为）
    private GridView gridView;    //用GrideView布局键盘，其实并不是真正的键盘，只是模拟键盘的功能

    private ArrayList<Map<String, String>> valueList;    //有人可能有疑问，为何这里不用数组了？
    //因为要用Adapter中适配，用数组不能往adapter中填充

    private RelativeLayout layoutBack;

    private Animation enterAnim;

    private Animation exitAnim;
    // 要展示输入结果的 输入框
    private EditText textAmount;

    public VirtualKeyboardView(Context context) {
        this(context, null);
    }

    public VirtualKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        View view = View.inflate(context, R.layout.layout_virtual_keyboard, null);

        valueList = new ArrayList<>();

        layoutBack = view.findViewById(R.id.layoutBack);

        gridView = view.findViewById(R.id.gv_keybord);

        initValueList();

        setupView();

        addView(view);      //必须要，不然不显示控件

        initAnim();

        gridView.setOnItemClickListener(onItemClickListener);
        layoutBack.setOnClickListener(this);
    }

    /**
     * 显示键盘
     */
    public void Opean() {
        setFocusable(true);
        setFocusableInTouchMode(true);

        startAnimation(enterAnim);
        setVisibility(View.VISIBLE);

    }


    private void initValueList() {

        // 初始化按钮上应该显示的数字
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", ".");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
    }


    private void setupView() {

        KeyBoardAdapter keyBoardAdapter = new KeyBoardAdapter(context, valueList);
        gridView.setAdapter(keyBoardAdapter);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.layoutBack) {
            startAnimation(exitAnim);
            setVisibility(View.GONE);
        }
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);
    }

    public void setView(EditText textAmount) {
        this.textAmount = textAmount;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = textAmount.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                textAmount.setText(amount);

                Editable ea = textAmount.getText();
                textAmount.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount = textAmount.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        textAmount.setText(amount);

                        Editable ea = textAmount.getText();
                        textAmount.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = textAmount.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        textAmount.setText(amount);

                        Editable ea = textAmount.getText();
                        textAmount.setSelection(ea.length());
                    }
                }
            }
        }
    };
}

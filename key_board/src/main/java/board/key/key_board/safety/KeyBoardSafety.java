package board.key.key_board.safety;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;

import board.key.key_board.R;

/**
 * @name Keyboard
 * @class name：board.key.key_board.safety
 * @anthor 家佑
 */
public class KeyBoardSafety extends RelativeLayout implements View.OnClickListener {
    private KhKeyboardView keyboardUtil;
    private View view;
    private Context mContext;
    //载入动画
    private Animation enterAnim;

    private Animation exitAnim;
    // 要展示输入结果的 输入框
    private EditText textAmount;

    public KeyBoardSafety(Context context) {
        super(context);
    }

    public KeyBoardSafety(Context context, AttributeSet attrs) {

        super(context, attrs);
        view = View.inflate(context, R.layout.keyboard_key_board, null);
        mContext = context;
        setupView();

        addView(view);      //必须要，不然不显示控件

        initAnim();
        view.findViewById(R.id.keyboard_finish).setOnClickListener(this);
        view.findViewById(R.id.keyboard_back_hide).setOnClickListener(this);
        //隐藏键盘
        setVisibility(View.GONE);
    }

    //设置布局
    private void setupView() {
        try {
            if (keyboardUtil == null)
                keyboardUtil = new KhKeyboardView(mContext, view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化动画
     */
    private void initAnim() {
        enterAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_out);
    }

    /**
     * 展示键盘
     *
     * @param editText
     */
    public void show(final EditText editText) {
        setFocusable(true);
        setFocusableInTouchMode(true);
        startAnimation(enterAnim);
        setVisibility(View.VISIBLE);
        keyboardUtil.showKeyboard(editText);
    }

    /**
     * 隐藏键盘
     */
    public void dismiss() {
        startAnimation(exitAnim);
        setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        try {
            int i = v.getId();
            if (i == R.id.keyboard_finish) {
                keyboardUtil.hideKeyboard();
                dismiss();

            } else if (i == R.id.keyboard_back_hide) {
                keyboardUtil.hideKeyboard();
                dismiss();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package dreamguys.in.co.gigs.dialogBuilder;


import dreamguys.in.co.gigs.dialogBuilder.effects.BaseEffects;
import dreamguys.in.co.gigs.dialogBuilder.effects.FadeIn;
import dreamguys.in.co.gigs.dialogBuilder.effects.FlipH;
import dreamguys.in.co.gigs.dialogBuilder.effects.FlipV;
import dreamguys.in.co.gigs.dialogBuilder.effects.NewsPaper;
import dreamguys.in.co.gigs.dialogBuilder.effects.SideFall;
import dreamguys.in.co.gigs.dialogBuilder.effects.SlideLeft;
import dreamguys.in.co.gigs.dialogBuilder.effects.SlideRight;
import dreamguys.in.co.gigs.dialogBuilder.effects.SlideTop;
import dreamguys.in.co.gigs.dialogBuilder.effects.Fall;
import dreamguys.in.co.gigs.dialogBuilder.effects.SlideBottom;
import dreamguys.in.co.gigs.dialogBuilder.effects.RotateBottom;
import dreamguys.in.co.gigs.dialogBuilder.effects.RotateLeft;
import dreamguys.in.co.gigs.dialogBuilder.effects.Slit;
import dreamguys.in.co.gigs.dialogBuilder.effects.Shake;


public enum Effectstype {

    Fadein(FadeIn.class),
    Slideleft(SlideLeft.class),
    Slidetop(SlideTop.class),
    SlideBottom(SlideBottom.class),
    Slideright(SlideRight.class),
    Fall(Fall.class),
    Newspager(NewsPaper.class),
    Fliph(FlipH.class),
    Flipv(FlipV.class),
    RotateBottom(RotateBottom.class),
    RotateLeft(RotateLeft.class),
    Slit(Slit.class),
    Shake(Shake.class),
    Sidefill(SideFall.class);
    private Class<? extends BaseEffects> effectsClazz;

    private Effectstype(Class<? extends BaseEffects> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffects getAnimator() {
        BaseEffects bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not init animatorClazz instance");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            throw new Error("Can not init animatorClazz instance");
        }
        return bEffects;
    }
}

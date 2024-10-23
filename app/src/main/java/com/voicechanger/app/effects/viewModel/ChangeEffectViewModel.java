package com.voicechanger.app.effects.viewModel;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.MutableLiveData;

import com.voicechanger.app.effects.R;
import com.voicechanger.app.effects.allBaseAct.BaseViewModel;
import com.voicechanger.app.effects.custUi.constatnt.AppDataException;
import com.voicechanger.app.effects.getApiData.allModel.EffectModel;
import com.voicechanger.app.effects.getApiData.allModel.TypeEffectModel;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

public final class ChangeEffectViewModel extends BaseViewModel {
    private List<EffectModel> effectList = new ArrayList();
    private List<TypeEffectModel> effectTypeList = new ArrayList();
    private MutableLiveData<List<TypeEffectModel>> liveType = new MutableLiveData<>();
    private int label;

    public List<TypeEffectModel> getEffectTypeList() {
        return this.effectTypeList;
    }

    public MutableLiveData<List<TypeEffectModel>> getLiveType() {
        return this.liveType;
    }

    public List<EffectModel> getEffectList() {
        return this.effectList;
    }

    public void getTypeEffects(Context ctx) {
        AppDataException.executeAsync(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) ctx), new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    getEffectTypeList().clear();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    List<TypeEffectModel> listType = getEffectTypeList();
                    String string = ctx.getString(R.string.all_effects);
                    listType.add(new TypeEffectModel(string, true));

                    List<TypeEffectModel> listType2 = getEffectTypeList();
                    String string2 = ctx.getString(R.string.scary_effects);
                    listType2.add(new TypeEffectModel(string2, false));

                    List<TypeEffectModel> listType3 = getEffectTypeList();
                    String string3 = ctx.getString(R.string.other_effects);
                    listType3.add(new TypeEffectModel(string3, false));

                    List<TypeEffectModel> listType4 = getEffectTypeList();
                    String string4 = ctx.getString(R.string.people_effects);
                    listType4.add(new TypeEffectModel(string4, false));

                    List<TypeEffectModel> listType5 = getEffectTypeList();
                    String string5 = ctx.getString(R.string.robot_effects);
                    return Boxing.boxBoolean(listType5.add(new TypeEffectModel(string5, false)));

                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                getLiveType().setValue(getEffectTypeList());
                return Unit.INSTANCE;
            }
        });
    }

    public List<EffectModel> getAllVoiceEffects(Context ctx) {
        AppDataException.executeAsync(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) ctx), new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    getEffectList().clear();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {

                    List<EffectModel> effectModels = getEffectList();
                    String s = ctx.getString(R.string.normal);
                    effectModels.add(new EffectModel(0, s, "normal", R.drawable.ic_normal_unselected, R.drawable.ic_normal_selected, 0, true));

                    List<EffectModel> effectModels6 = getEffectList();
                    String s6 = ctx.getString(R.string.drunk);
                    effectModels6.add(new EffectModel(6, s6, "drunk", R.drawable.ic_drunk_unselected, R.drawable.ic_drunk_selected, 0, false));

                    List<EffectModel> effectModels10 = getEffectList();
                    String s10 = ctx.getString(R.string.reverse);
                    effectModels10.add(new EffectModel(10, s10, "reverse", R.drawable.ic_reverse_unselected, R.drawable.ic_reverse_selected, 0, false));

                    List<EffectModel> effectModels18 = getEffectList();
                    String s18 = ctx.getString(R.string.zombie);
                    effectModels18.add(new EffectModel(18, s18, "zombie", R.drawable.ic_zombie_unselected, R.drawable.ic_zombie_selected, 0, false));

                    List<EffectModel> effectModels2 = getEffectList();
                    String s2 = ctx.getString(R.string.robot);
                    effectModels2.add(new EffectModel(2, s2, "robot", R.drawable.ic_roboto_unselected, R.drawable.ic_roboto_selected, 0, false));

                    List<EffectModel> effectModels5 = getEffectList();
                    String s5 = ctx.getString(R.string.nervous);
                    effectModels5.add(new EffectModel(5, s5, "nervous", R.drawable.ic_nervous_unselected, R.drawable.ic_nervous_selected, 0, false));

                    List<EffectModel> effectModels9 = getEffectList();
                    String s9 = ctx.getString(R.string.death);
                    effectModels9.add(new EffectModel(9, s9, "death", R.drawable.ic_death_unselected, R.drawable.ic_death_selected, 0, false));

                    List<EffectModel> effectModels1 = getEffectList();
                    String s1 = ctx.getString(R.string.helium);
                    effectModels1.add(new EffectModel(1, s1, "helium", R.drawable.ic_helium_unselected, R.drawable.ic_helium_selected, 0, false));

                    List<EffectModel> effectModels7 = getEffectList();
                    String s7 = ctx.getString(R.string.squirrel);
                    effectModels7.add(new EffectModel(7, s7, "squirrel", R.drawable.ic_squirrel_unselected, R.drawable.ic_squirrel_selected, 0, false));

                    List<EffectModel> effectModels4 = getEffectList();
                    String s4 = ctx.getString(R.string.monster);
                    effectModels4.add(new EffectModel(4, s4, "monster", R.drawable.ic_monster_unselected, R.drawable.ic_monster_selected, 0, false));

                    List<EffectModel> effectModels13 = getEffectList();
                    String s13 = ctx.getString(R.string.big_robot);
                    effectModels13.add(new EffectModel(13, s13, "bigrobot", R.drawable.ic_big_roboto_unselected, R.drawable.ic_big_roboto_selected, 0, false));

                    List<EffectModel> effectModels20 = getEffectList();
                    String s20 = ctx.getString(R.string.alien);
                    effectModels20.add(new EffectModel(20, s20, "alien", R.drawable.ic_alien_unselected, R.drawable.ic_alien_selected, 0, false));

                    List<EffectModel> effectModels8 = getEffectList();
                    String s8 = ctx.getString(R.string.child);
                    effectModels8.add(new EffectModel(8, s8, "child", R.drawable.ic_child_unselected, R.drawable.ic_child_selected, 0, false));

                    List<EffectModel> effectModels15 = getEffectList();
                    String s15 = ctx.getString(R.string.underwater);
                    effectModels15.add(new EffectModel(15, s15, "underwater", R.drawable.ic_underwater_unselected, R.drawable.ic_underwater_selected, 0, false));

                    List<EffectModel> effectModels12 = getEffectList();
                    String s12 = ctx.getString(R.string.hexafluoride);
                    effectModels12.add(new EffectModel(12, s12, "hexafluoride", R.drawable.ic_hexafluoride_unselected, R.drawable.ic_hexafluoride_selected, 0, false));

                    List<EffectModel> effectModels21 = getEffectList();
                    String s21 = ctx.getString(R.string.small_alien);
                    effectModels21.add(new EffectModel(21, s21, "smallalien", R.drawable.back_chimp_unseleted, R.drawable.back_chimp_selected, 0, false));

                    List<EffectModel> effectModels14 = getEffectList();
                    String s14 = ctx.getString(R.string.telephone);
                    effectModels14.add(new EffectModel(14, s14, "telephone", R.drawable.ic_telephone_unselected, R.drawable.ic_telephone_selected, 0, false));

                    List<EffectModel> effectModels16 = getEffectList();
                    String s16 = ctx.getString(R.string.extraterrestrial);
                    effectModels16.add(new EffectModel(16, s16, "extraterrestrial", R.drawable.ic_extraterrestrial_unselected, R.drawable.ic_extraterrestrial_selected, 0, false));

                    List<EffectModel> effectModels3 = getEffectList();
                    String s3 = ctx.getString(R.string.cave);
                    effectModels3.add(new EffectModel(3, s3, "cave", R.drawable.ic_cave_unselected, R.drawable.ic_cave_selected, 0, false));

                    List<EffectModel> effectModels19 = getEffectList();
                    String s19 = ctx.getString(R.string.megaphone);
                    effectModels19.add(new EffectModel(19, s19, "megaphone", R.drawable.ic_megaphone_unselected, R.drawable.ic_megaphone_selected, 0, false));

                    List<EffectModel> effectModels17 = getEffectList();
                    String s17 = ctx.getString(R.string.villain);
                    effectModels17.add(new EffectModel(17, s17, "villain", R.drawable.ic_villain_unselected, R.drawable.ic_villain_selected, 0, false));

                    List<EffectModel> effectModels22 = getEffectList();
                    String s22 = ctx.getString(R.string.back_chipmunks);
                    effectModels22.add(new EffectModel(22, s22, "backchipmunk", R.drawable.back_chimp_unseleted, R.drawable.back_chimp_selected, 0, false));

                    List<EffectModel> effectModels11 = getEffectList();
                    String s11 = ctx.getString(R.string.grand_canyon);
                    return Boxing.boxBoolean(effectModels11.add(new EffectModel(11, s11, "grandcanyon", R.drawable.ic_grand_canyon_unselected, R.drawable.ic_grand_canyon_selected, 0, false)));
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
//                invoke(o);
                return Unit.INSTANCE;
            }
        });
        return this.effectList;
    }

    public List<EffectModel> getAllRobotEffects(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "context");
        AppDataException.executeAsync(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) ctx), new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    getEffectList().clear();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {

                    List<EffectModel> effectModels1 = getEffectList();
                    String s1 = ctx.getString(R.string.big_robot);
                    effectModels1.add(new EffectModel(13, s1, "bigrobot", R.drawable.ic_big_roboto_unselected, R.drawable.ic_big_roboto_selected, 0, false));

                    List<EffectModel> effectModels = getEffectList();
                    String s = ctx.getString(R.string.robot);
                    return Boxing.boxBoolean(effectModels.add(new EffectModel(2, s, "robot", R.drawable.ic_roboto_unselected, R.drawable.ic_roboto_selected, 0, false)));
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                return Unit.INSTANCE;
            }
        });
        return this.effectList;
    }

    public List<EffectModel> getAllPeopleEffects(Context ctx) {
        AppDataException.executeAsync(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) ctx), new Function1() {
            @Override
            public Object invoke(Object o) {
                return null;
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {

                    List<EffectModel> effectList3 = getEffectList();
                    String s2 = ctx.getString(R.string.child);
                    effectList3.add(new EffectModel(8, s2, "child", R.drawable.ic_child_unselected, R.drawable.ic_child_selected, 0, false));

                    List<EffectModel> effectList6 = getEffectList();
                    String s4 = ctx.getString(R.string.nervous);
                    effectList6.add(new EffectModel(5, s4, "nervous", R.drawable.ic_nervous_unselected, R.drawable.ic_nervous_selected, 0, false));

                    List<EffectModel> effectList5 = getEffectList();
                    String s = ctx.getString(R.string.villain);
                    effectList5.add(new EffectModel(17, s, "villain", R.drawable.ic_villain_unselected, R.drawable.ic_villain_selected, 0, false));

                    List<EffectModel> effectList4 = getEffectList();
                    String s1 = ctx.getString(R.string.underwater);
                    effectList4.add(new EffectModel(15, s1, "underwater", R.drawable.ic_underwater_unselected, R.drawable.ic_underwater_selected, 0, false));

                    List<EffectModel> effectList2 = getEffectList();
                    String s3 = ctx.getString(R.string.drunk);
                    return Boxing.boxBoolean(effectList2.add(new EffectModel(6, s3, "drunk", R.drawable.ic_drunk_unselected, R.drawable.ic_drunk_selected, 0, false)));

                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, o -> Unit.INSTANCE);
        return this.effectList;
    }

    public List<EffectModel> getAllScaryEffects(Context ctx) {
        AppDataException.executeAsync(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) ctx), new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    getEffectList().clear();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    List<EffectModel> effectList5 = getEffectList();
                    String s4 = ctx.getString(R.string.zombie);
                    effectList5.add(new EffectModel(18, s4, "zombie", R.drawable.ic_zombie_unselected, R.drawable.ic_zombie_selected, 0, false));

                    List<EffectModel> effectList2 = getEffectList();
                    String s1 = ctx.getString(R.string.alien);
                    effectList2.add(new EffectModel(20, s1, "alien", R.drawable.ic_alien_unselected, R.drawable.ic_alien_selected, 0, false));

                    List<EffectModel> effectList6 = getEffectList();
                    String s5 = ctx.getString(R.string.small_alien);
                    effectList6.add(new EffectModel(21, s5, "smallalien", R.drawable.ic_small_alien_unselected, R.drawable.ic_small_alien_selected, 0, false));

                    List<EffectModel> effectList1 = getEffectList();
                    String s = ctx.getString(R.string.monster);
                    effectList1.add(new EffectModel(4, s, "monster", R.drawable.ic_monster_unselected, R.drawable.ic_monster_unselected, 0, false));

                    List<EffectModel> effectList4 = getEffectList();
                    String s3 = ctx.getString(R.string.extraterrestrial);
                    effectList4.add(new EffectModel(16, s3, "extraterrestrial", R.drawable.ic_extraterrestrial_unselected, R.drawable.ic_extraterrestrial_selected, 0, false));

                    List<EffectModel> effectList3 = getEffectList();
                    String s2 = ctx.getString(R.string.death);
                    return Boxing.boxBoolean(effectList3.add(new EffectModel(9, s2, "death", R.drawable.ic_death_unselected, R.drawable.ic_death_selected, 0, false)));
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                return Unit.INSTANCE;
            }
        });
        return this.effectList;
    }

    public List<EffectModel> getAllOtherEffects(Context ctx) {
        AppDataException.executeAsync(LifecycleOwnerKt.getLifecycleScope((LifecycleOwner) ctx), new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {
                    getEffectList().clear();
                    return Unit.INSTANCE;
                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                IntrinsicsKt.getCOROUTINE_SUSPENDED();
                if (label == 0) {


                    List<EffectModel> effectList3 = getEffectList();
                    String s2 = ctx.getString(R.string.cave);
                    effectList3.add(new EffectModel(3, s2, "cave", R.drawable.ic_cave_unselected, R.drawable.ic_cave_selected, 0, false));

                    List<EffectModel> effectList8 = getEffectList();
                    String s7 = ctx.getString(R.string.megaphone);
                    effectList8.add(new EffectModel(19, s7, "megaphone", R.drawable.ic_megaphone_unselected, R.drawable.ic_megaphone_selected, 0, false));

                    List<EffectModel> effectList5 = getEffectList();
                    String s4 = ctx.getString(R.string.squirrel);
                    effectList5.add(new EffectModel(7, s4, "squirrel", R.drawable.ic_squirrel_unselected, R.drawable.ic_squirrel_selected, 0, false));

                    List<EffectModel> effectList6 = getEffectList();
                    String s5 = ctx.getString(R.string.telephone);
                    effectList6.add(new EffectModel(14, s5, "telephone", R.drawable.ic_telephone_unselected, R.drawable.ic_telephone_selected, 0, false));

                    List<EffectModel> effectList2 = getEffectList();
                    String s1 = ctx.getString(R.string.hexafluoride);
                    effectList2.add(new EffectModel(12, s1, "hexafluoride", R.drawable.ic_hexafluoride_unselected, R.drawable.ic_hexafluoride_selected, 0, false));

                    List<EffectModel> effectList9 = getEffectList();
                    String s8 = ctx.getString(R.string.grand_canyon);
                    effectList9.add(new EffectModel(11, s8, "grandcanyon", R.drawable.ic_grand_canyon_unselected, R.drawable.ic_grand_canyon_selected, 0, false));

                    List<EffectModel> effectList7 = getEffectList();
                    String s6 = ctx.getString(R.string.reverse);
                    effectList7.add(new EffectModel(10, s6, "reverse", R.drawable.ic_reverse_unselected, R.drawable.ic_reverse_selected, 0, false));

                    List<EffectModel> effectList1 = getEffectList();
                    String s = ctx.getString(R.string.helium);
                    effectList1.add(new EffectModel(1, s, "helium", R.drawable.ic_helium_unselected, R.drawable.ic_helium_selected, 0, false));

                    List<EffectModel> effectList4 = getEffectList();
                    String s3 = ctx.getString(R.string.back_chipmunks);
                    return Boxing.boxBoolean(effectList4.add(new EffectModel(22, s3, "backchipmunk", R.drawable.back_chimp_unseleted, R.drawable.back_chimp_selected, 0, false)));

                }
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        }, new Function1() {
            @Override
            public Object invoke(Object o) {
                return Unit.INSTANCE;
            }
        });
        return this.effectList;
    }

}

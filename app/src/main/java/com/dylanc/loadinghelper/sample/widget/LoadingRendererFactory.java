package com.dylanc.loadinghelper.sample.widget;

import android.content.Context;
import android.util.SparseArray;
import com.dylanc.loadinghelper.sample.widget.renderer.ElectricFanLoadingRenderer;

import java.lang.reflect.Constructor;

final class LoadingRendererFactory {
    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS = new SparseArray<>();

    static {
        LOADING_RENDERERS.put(9, ElectricFanLoadingRenderer.class);
    }

    private LoadingRendererFactory() {
    }

    static LoadingRenderer createLoadingRenderer(Context context, int loadingRendererId) throws Exception {
        Class<?> loadingRendererClazz = LOADING_RENDERERS.get(loadingRendererId);
        Constructor<?>[] constructors = loadingRendererClazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }

        throw new InstantiationException();
    }
}

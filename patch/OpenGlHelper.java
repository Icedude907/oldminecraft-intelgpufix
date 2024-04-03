// This is a general fix for all versions.
// Each version will require slightly different changes to work,
// along with being recompiled using different obfuscation mappings.

package net.minecraft.src;

import org.lwjgl.opengl.*;

public class OpenGlHelper {

  public static int lightmapDisabled;
  public static int lightmapEnabled;
  private static boolean useMultitextureARB = false;

  public static void initializeTextures() {
    useMultitextureARB = GLContext.getCapabilities().GL_ARB_multitexture && !GLContext.getCapabilities().OpenGL13;
    if (useMultitextureARB) {
      lightmapDisabled = 33984 /*GL_TEXTURE0_ARB*/;
      lightmapEnabled = 33985 /*GL_TEXTURE1_ARB*/;
    } else {
      lightmapDisabled = 33984 /*GL_TEXTURE0_ARB*/;
      lightmapEnabled = 33985 /*GL_TEXTURE1_ARB*/;
    }
  }

  public static void setActiveTexture(int i) {
    if (useMultitextureARB) {
      ARBMultitexture.glClientActiveTextureARB(i);
      ARBMultitexture.glActiveTextureARB(i);
    } else {
      // The core of the change is adding this line back in. It was removed, presumably because there was no impact on rendering at the time.
      // A change in the intel gpu drivers caused this to surface as an error - I'm not sure whether intel or minecraft is at fault.
      // To my knowledge, this discovery was made by PheonixVK, which TheMasterCaver documented here (https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/1294926-themastercavers-world?page=13#c294)
      GL13.glClientActiveTexture(i);
      GL13.glActiveTexture(i);
    }
  }

  public static void setClientActiveTexture(int i) {
    if (useMultitextureARB) {
      ARBMultitexture.glClientActiveTextureARB(i);
    } else {
      GL13.glClientActiveTexture(i);
    }
  }

  public static void setLightmapTextureCoords(int i, float f, float f1) {
    if (useMultitextureARB) {
      ARBMultitexture.glMultiTexCoord2fARB(i, f, f1);
    } else {
      GL13.glMultiTexCoord2f(i, f, f1);
    }
  }
}

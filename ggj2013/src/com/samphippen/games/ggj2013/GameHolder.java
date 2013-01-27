package com.samphippen.games.ggj2013;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.samphippen.games.ggj2013.pathfind.AStarPathFinder;
import com.samphippen.games.ggj2013.pathfind.ContinuousPathFinder;
import com.samphippen.games.ggj2013.sound.SoundManager;

public class GameHolder implements ApplicationListener {
    private static GameHolder sSharedInstance = null;

    public static GameHolder getInstance() {
        return sSharedInstance;
    }

    private OrthographicCamera mCamera;
    private SpriteBatch mBatch;
    private SpriteBatch mSpecialBatch;
    private BackgroundObject mBackground;

    private final List<GameObject> mWorldObjects = new ArrayList<GameObject>();
    private final List<Renderable> mToRender = new ArrayList<Renderable>();
    private final RenderQueueProxy mQueueProxy = new ListRenderQueueProxy(
            mToRender);
    private PlayerObject mPlayer;
    private MouseObject mMouse;
    private ShaderProgram mShader;
    private final List<CampfireSprite> mPathSprites = new ArrayList<CampfireSprite>();

    private SoundManager mSoundManager;
    private SpriteBatch mPathBatch;
    private boolean mDrawWin = false;
    private OrangeBlob mOb;
    private Sprite mWinSprite;
    private ChaserObject mChaser;
    private boolean mDrawLose = false;
    private Sprite mLoseSprite;
    private float mPulseR = 1.0f;
    private float mPulseB = 1.0f;
    float mPulseG = 1.0f;
    private boolean mSplash = true;
    private Sprite mSplashSprite;

    float mRadialAdjust = 0.0f;
    private long mLastAmplificationTick = 0;

    private static final int NLIGHTS = 8;
    private final LightManager mLightManager = new LightManager();
    private int mGuardFrames;
    private int mFirstPulseCounter;
    private boolean mWhitePulseCalled = true;
    private SmokeObject mFog;

    public LightManager getLightManager() {
        return mLightManager;
    }

    public SoundManager getSoundManager() {
        return mSoundManager;
    }

    private static String createLocalLightUniformDeclaration() {
        String eyes = "";
        for (int i = 0; i < NLIGHTS; ++i) {
            eyes += "uniform vec4 localLight" + i + ";\n";
            eyes += "uniform float localLightIntensity" + i + ";\n";
        }
        return eyes;
    }

    private static String createLocalLightLightnessModifier() {
        String eyes = "";
        for (int i = 0; i < NLIGHTS; ++i) {
            eyes += "{ float local_distance = distance(gl_FragCoord.xy, localLight"
                    + i + ".xy);";
            eyes += "if (local_distance < localLight" + i
                    + ".z) { lightness += localLightIntensity" + i + "; }";
            eyes += "else if (local_distance < localLight" + i + ".w) {";
            eyes += "lightness += localLightIntensity" + i
                    + " * (1.0 - ((local_distance - localLight" + i
                    + ".z) / (localLight" + i + ".w - localLight" + i
                    + ".z)));";
            eyes += "} }";
        }
        return eyes;
    }

    @Override
    public void create() {
        assert sSharedInstance == null : "duplicate GameHolder";
        sSharedInstance = this;
        float nativeGamma = 2.4f;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            nativeGamma = 1.8f;
        }
        String vertexShader = "attribute vec4 "
                + ShaderProgram.POSITION_ATTRIBUTE
                + ";\n" //
                + "attribute vec4 "
                + ShaderProgram.COLOR_ATTRIBUTE
                + ";\n" //
                + "attribute vec2    "
                + ShaderProgram.TEXCOORD_ATTRIBUTE
                + "0;\n" //
                + "uniform mat4 u_proj;\n"
                + "uniform mat4 u_trans;\n"
                + "uniform mat4 u_projTrans;\n"
                + "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "\n" //
                + "void main()\n" //
                + "{\n" //
                + "   v_color = "
                + ShaderProgram.COLOR_ATTRIBUTE
                + ";\n" //
                + "   v_texCoords = "
                + ShaderProgram.TEXCOORD_ATTRIBUTE
                + "0;\n" //
                + "   gl_Position =  u_projTrans * "
                + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
                + "}\n";
        String fragmentShader = "varying vec4 v_color;\n" //
                + "varying vec2 v_texCoords;\n" //
                + "uniform sampler2D u_texture;\n" //
                + "uniform float glow_radius;"
                + "uniform float mute;"
                + "uniform float radial_a;"
                + "uniform float radial_b;"
                + "uniform float channel_r;"
                + "uniform float channel_g;"
                + "uniform float channel_b;" + "uniform float band_width;"
                + createLocalLightUniformDeclaration()
                + "void main()\n"//
                + "{\n" //
                + "  float centerX = 400.0;"
                + "  float centerY = 300.0;"
                + "  float fx = gl_FragCoord.x;"
                + "  float fy = gl_FragCoord.y;"
                + "  float dx = fx-centerX;"
                + "  float dy = fy-centerY;"
                + "  float lightness = 0.0;"
                + "  float uselight = -1.0;"
                + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n"
                + "  float tmp = gl_FragColor[3];"
                + "  float r = gl_FragColor[0];"
                + "  float g = gl_FragColor[1];"
                + "  float b = gl_FragColor[2];"
                + "  float k = -1.0;"
                + "  float current_radius = sqrt(dx * dx + dy * dy);"
                + "  if (current_radius < glow_radius && current_radius > glow_radius - band_width) {"
                + "     float into = (glow_radius-current_radius)/(band_width); "
                + "     float per = (into < 0.5) ? into * 2.0 : (1.0-into)*2.0; "
                + "     lightness += clamp(per*(1.0-(glow_radius/300.0)), 0.0, 1.0);"
                + "  }"
                + "  centerX = 400.0; centerY = 300.0; dx = fx-centerX; dy = fy-centerY;"
                + "  if (dx * dx + dy * dy < 0.0) {"
                + "    gl_FragColor[0] = r;"
                + "    gl_FragColor[1] = g;"
                + "    gl_FragColor[2] = b;"
                + "    uselight = 1.0;"
                + "  } else if (dx * dx + dy * dy < 200000.0) {"
                + "     float per = radial_a/(abs(dx*dx)+abs(dy*dy)+radial_b);"
                + "     lightness += per*0.1;"
                + "  }"
                + createLocalLightLightnessModifier()
                // TODO change to 0.1
                //+ " if (lightness < 1.0) lightness = 1.0;"
                + " if (lightness < 0.0) lightness = 0.0;"
                + " if (lightness > 1.0) lightness = 1.0;"
                + "  if (uselight == -1.0) gl_FragColor *= lightness;"
                + "  if (1 == 1) {"
                + "     float intensity = 0.3 * gl_FragColor[0] + 0.59 * gl_FragColor[1] + 0.11 * gl_FragColor[2];"
                + "     gl_FragColor[0] = (intensity * mute + gl_FragColor[0] * (1.0 - mute))*channel_r;"
                + "     gl_FragColor[1] = (intensity * mute + gl_FragColor[1] * (1.0 - mute))*channel_g;"
                + "     gl_FragColor[2] = (intensity * mute + gl_FragColor[2] * (1.0 - mute))*channel_b;"
                + "  }"
                + "  gl_FragColor[3] = tmp;"
                + "const float GAMMA_ADJUST = (2.4 / "
                + nativeGamma
                + ");"
                + "gl_FragColor[0] = pow(gl_FragColor[0], GAMMA_ADJUST);"
                + "gl_FragColor[1] = pow(gl_FragColor[1], GAMMA_ADJUST);"
                + "gl_FragColor[2] = pow(gl_FragColor[2], GAMMA_ADJUST);" + "}";

        ShaderProgram.pedantic = false;

        Constants.setConstants();
        mWinSprite = GameServices.loadSprite("winscreen.png");
        mLoseSprite = GameServices.loadSprite("losescreen.png");
        mSplashSprite = GameServices.loadSprite("splashscreen.png");
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        mCamera = new OrthographicCamera(w, h);
        mBatch = new SpriteBatch();

        mShader = new ShaderProgram(vertexShader, fragmentShader);
        if (!mShader.isCompiled()) {
            throw new IllegalArgumentException("couldn't compile shader: "
                    + mShader.getLog());
        }
        mBatch.setShader(mShader);
        mPathBatch = new SpriteBatch();
        mSpecialBatch = new SpriteBatch();
        mPlayer = PlayerObject.getInstance();

        mBackground = new BackgroundObject();
        mMouse = MouseObject.getInstance();
        mChaser = new ChaserObject();
        mWorldObjects.add(mBackground);
        mWorldObjects.add(mPlayer);
        mWorldObjects.add(mChaser);
        
        // Add obstacles to the world
        ContinuousPathFinder cpf = new ContinuousPathFinder(
                new AStarPathFinder(), GameServices.PATH_FINDER_WIDTH,
                GameServices.PATH_FINDER_HEIGHT);
        ObstaclesFactory obstaclesFactory = new ObstaclesFactory(mWorldObjects,
                cpf);
        obstaclesFactory.makeObstacles();
        
        mOb = new OrangeBlob();

        mFog = new SmokeObject();
        // Distance to winning point destination
        double endPointMinDistance = Constants.sConstants
                .get("end_point_distance_min");
        float endPointRandomisedDistance = (float) (Constants.sConstants
                .get("end_point_distance_max") - endPointMinDistance);
        List<Vector2> path;
        do {
            Vector2 destination = new Vector2(endPointRandomisedDistance
                    * GameServices.sRng.nextFloat()
                    + endPointRandomisedDistance, endPointRandomisedDistance
                    * GameServices.sRng.nextFloat()
                    + endPointRandomisedDistance);
            if (GameServices.sRng.nextBoolean()) {
                destination.x = -destination.x;
            }
            if (GameServices.sRng.nextBoolean()) {
                destination.y = -destination.y;
            }

            path = cpf.findPath(
                    GameServices.toPathFinder(mPlayer.getPosition()),
                    GameServices.toPathFinder(destination));
        } while (path == null);

        Vector2 prev = mPlayer.getPosition();

        for (Vector2 v : path) {
            // Sprite s = GameServices.loadSprite("campfire.png");
            v = GameServices.fromPathFinder(v);
            if (new Vector2(prev).sub(v).len() > Constants.sConstants
                    .get("waypoint_spacing")) {
                Light campfire = mLightManager.createLight(v);
                CampfireSprite cs = new CampfireSprite(campfire);
                cs.setPosition(v);
                mPathSprites.add(cs);
                mWorldObjects.add(cs);
                prev = v;
            }
        }
        
        // Make tree ring for starting position
        obstaclesFactory.makeTreeRing(mPathSprites.get(0).mPosition.angle());

        // mPathSprites.get(mPathSprites.size() - 1).setColor(1, 1, 1, 1);

        Gdx.input.setCursorCatched(true);

        mSoundManager = new SoundManager();
        whitePulse();
    }

    @Override
    public void dispose() {
        mBatch.dispose();
    }

    @Override
    public void render() {
        if (!mDrawWin && !mDrawLose && !mSplash) {
            update();
            draw();
        } else if (mDrawWin) {
            drawWin();
        } else if (mDrawLose) {
            drawLose();
        } else if (mSplash) {
            drawSplash();
        }

    }
    
    public Vector2 getFirstOnFire() {
        for (CampfireSprite cs : mPathSprites) {
            if (cs.getOn()) {
                return new Vector2(cs.getX(), cs.getY());
            }
        }
        return null;
    }

    private void drawSplash() {
        mSpecialBatch.begin();
        mSplashSprite.setPosition(0, 0);
        mSplashSprite.draw(mSpecialBatch);
        mSpecialBatch.end();
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            mSplash = false;
            mSoundManager.beatHeart();
        }
    }

    private void drawLose() {
        Gdx.input.setCursorCatched(false);
        mSpecialBatch.begin();
        mLoseSprite.setPosition(-400, -300);
        mLoseSprite.draw(mSpecialBatch);
        mSpecialBatch.end();
    }

    private void drawWin() {
        Gdx.input.setCursorCatched(false);
        mSpecialBatch.begin();
        mWinSprite.setPosition(-400, -300);
        mWinSprite.draw(mSpecialBatch);
        mSpecialBatch.end();
    }

    private void update() {
        mRadialAdjust *= 0.98f;
        if (mRadialAdjust > 0.01f) {
            System.out.printf("RA = %f\n", mRadialAdjust);
        }
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            System.exit(1);
        }
        mGuardFrames -= 1;
        for (GameObject o : mWorldObjects) {
            o.update();
        }

        /*
         * for (CampfireSprite cs : mPathSprites) { cs.update(); }
         */

        mMouse.update();
        
        mOb.update();

        GameServices.advanceTicks();
        mFog.update();
        mFog.setPosition(getCameraOrigin().x, getCameraOrigin().y);

        CampfireSprite endSprite = mPathSprites.get(mPathSprites.size() - 1);
        if (new Vector2(mPlayer.getPosition()).sub(endSprite.getX(),
                endSprite.getY()).len() < 40) {
            mDrawWin = true;
        }

        if (new Vector2(mPlayer.getPosition()).sub(mChaser.getPosition()).len() < Constants
                .getFloat("die_radius")) {
            mDrawLose = true;
        }

        Vector2 mouseSpeed = InputSystem.mouseSpeedVector();
        float totalSpeed = mouseSpeed.len();
        int stepTime = 1; // prevents usage
        if (totalSpeed > 0.4) {
            stepTime = (int) (30 / Math.pow(totalSpeed, 0.27));
        }
        if (GameServices.getTicks() % stepTime == 2) {
            mSoundManager.step();
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        mBatch.setProjectionMatrix(mCamera.combined);
        mBatch.setShader(mShader);
        mBatch.setTransformMatrix(new Matrix4().translate(-getCameraOrigin().x,
                -getCameraOrigin().y, 0));

        mToRender.clear();
        for (GameObject object : mWorldObjects) {
            object.emitRenderables(mQueueProxy);
        }
        mFog.emitRenderables(mQueueProxy);
        mQueueProxy.commit();

        mBatch.begin();

        setShaderValues();
        for (Renderable renderable : mToRender) {
            renderable.draw(mBatch);
        }
        mBatch.end();
        /*
         * mPathBatch.setProjectionMatrix(mCamera.combined);
         * mPathBatch.setTransformMatrix(new Matrix4().translate(
         * -getCameraOrigin().x, -getCameraOrigin().y, 0)); mPathBatch.begin();
         * for (CampfireSprite s : mPathSprites) { s.draw(mPathBatch); }
         * mPathBatch.end();
         */

        mSpecialBatch.setProjectionMatrix(mCamera.combined);
        mSpecialBatch.begin();
        mMouse.draw(mSpecialBatch);
        mOb.draw(mSpecialBatch);
        mSpecialBatch.end();

    }

    private void setShaderValues() {
        float glowRadius = mPlayer.mHeartbeatRadius;
        float maxGlowRadius = 300;
        if (mPlayer.HeartBeatParameters.chaserPulseCount == 0
                && mPlayer.HeartBeatParameters.isFastHeartbeat()
                && mWhitePulseCalled) {

            System.out.println("sup");
            glowRadius *= Constants.getFloat("big_pulse_amp");
            maxGlowRadius *= Constants.getFloat("big_pulse_amp");
        }
        mShader.setUniform1fv("glow_radius", new float[] { glowRadius * 1 }, 0,
                1);

        mShader.setUniform1fv("channel_r", new float[] { mPulseR }, 0, 1);
        mShader.setUniform1fv("channel_g", new float[] { mPulseG }, 0, 1);
        mShader.setUniform1fv("channel_b", new float[] { mPulseB }, 0, 1);
        mShader.setUniform1fv("max_glow_radius",
                new float[] { maxGlowRadius * 1 }, 0, 1);

        mShader.setUniform1fv(
                "radial_a",
                new float[] { (float) (1 * Constants.sConstants
                        .get("radial_lighting_a") * (1.0f + 4.0f * mRadialAdjust)) },
                0, 1);
        mShader.setUniform1fv("radial_b",
                new float[] { (float) (1 * Constants.sConstants
                        .get("radial_lighting_b") * (1.0f - mRadialAdjust)) },
                0, 1);
        mShader.setUniform1fv("band_width",
                new float[] { (float) (1 * Constants.sConstants
                        .get("band_width") * (1.0f + mRadialAdjust)) }, 0, 1);
        float mv = 0.6f - 0.6f * glowRadius;
        if (mv < 0) {
            mv = 0;
        }
        if (mv > 0.6f) {
            mv = 0.6f;
        }
        mShader.setUniform1fv("mute", new float[] { mv }, 0, 1);
        int lightIndex = 0;
        Vector2 playerPosition = PlayerObject.getInstance().getPosition();
        for (Light light : mLightManager.lightsNearest(playerPosition, NLIGHTS)) {
            Vector2 lightPos = light.getPosition();
            Vector2 lightPosScreenSpace = new Vector2(lightPos.x
                    - playerPosition.x + 400, lightPos.y - playerPosition.y
                    + 300);
            mShader.setUniform4fv("localLight" + lightIndex,
                    new float[] { lightPosScreenSpace.x, lightPosScreenSpace.y,
                            light.getInnerRadius(), light.getOuterRadius() },
                    0, 4);
            mShader.setUniform1fv("localLightIntensity" + lightIndex,
                    new float[] { light.getIntensity() }, 0, 1);
            ++lightIndex;
        }
        for (; lightIndex < NLIGHTS; ++lightIndex) {
            mShader.setUniform4fv("localLight" + lightIndex, new float[] {
                    -10.0f, -10.0f, 0.0f, 1.0f }, 0, 1);
            mShader.setUniform1fv("localLightIntensity" + lightIndex,
                    new float[] { 0.0f }, 0, 1);
        }
    }

    private Vector2 getCameraOrigin() {
        return new Vector2(mPlayer.getPosition()).add(8, 8);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void amplifyPulse() {
        long currentTick = GameServices.getTicks();
        if (currentTick - mLastAmplificationTick < 240) {
            return;
        }
        mLastAmplificationTick = currentTick;
        mRadialAdjust = 1.0f;
    }

    public void redPulse() {
        if (mPlayer.HeartBeatParameters.chaserPulseCount == 1) {
            mWhitePulseCalled = false;
        }
        mPulseR = 1.0f;
        mPulseG = 0.3f;
        mPulseB = 0.3f;
    }

    public void whitePulse() {
        mPulseR = 1f;
        mWhitePulseCalled = true;
        if (mPulseG < 1.0) {
            System.out.println(mPulseG);
            mPulseG += Constants.getFloat("red_decay_rate");
        } else {
            mPulseG = 1.0f;
        }

        if (mPulseB < 1.0) {
            mPulseB += Constants.getFloat("red_decay_rate");
        } else {
            mPulseB = 1.0f;
        }
    }
}

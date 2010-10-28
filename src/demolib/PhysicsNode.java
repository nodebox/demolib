package demolib;

import net.phys2d.raw.strategies.QuadSpaceStrategy;
import nodebox.node.*;
import processing.core.PGraphics;
import net.phys2d.math.Vector2f;
import net.phys2d.raw.Body;
import net.phys2d.raw.BodyList;
import net.phys2d.raw.StaticBody;
import net.phys2d.raw.World;
import net.phys2d.raw.shapes.Box;

@Description("Demonstrate some physics.")
@Category("Demolib")
public class PhysicsNode extends Node {

    public final IntPort pAmount = new IntPort(this, "amount", Port.Direction.INPUT, 5);
    private int currentAmount = -1;
    
    private World world;
    private float time = 0;
    
    public void createTheWorld() {
        currentAmount = pAmount.get();
        
        world = new World(new Vector2f(0.0f, 10.0f), 10, new QuadSpaceStrategy(20,5));
        world.clear();

		Body body1 = new StaticBody("Ground1", new Box(400.0f, 20.0f));
		body1.setPosition(250.0f, 400);
		world.add(body1);
		Body body3 = new StaticBody("Ground2", new Box(200.0f, 20.0f));
		body3.setPosition(360.0f, 380);
		world.add(body3);
		Body body5 = new StaticBody("Ground3", new Box(20.0f, 100.0f));
		body5.setPosition(200.0f, 300);
		world.add(body5);
		Body body6 = new StaticBody("Ground3", new Box(20.0f, 100.0f));
		body6.setPosition(400.0f, 300);
		world.add(body6);

        for (int i=0;i<currentAmount;i++) {
            float sz =10 + (float)(Math.random() * 50f);
            Body b = new Body("b" + i, new Box(sz, sz), 100f);
            b.setPosition(100f + (float) (Math.random() * 250f), (float) (Math.random() * 250f));
            world.add(b);
        }
    }
    

    @Override
    public void execute(Context context, float time) {
        if (pAmount.get() != currentAmount) {
            createTheWorld();
        }
        float dt = time - this.time;
        world.step(dt);
        this.time = time;
    }

    @Override
    public void draw(PGraphics g, Context context, float v) {
        BodyList bodies = world.getBodies();

        for (int i=0;i<bodies.size();i++) {
            Body body = bodies.get(i);
            drawBoxBody(g,body,(Box) body.getShape());
        }
        
    }

    protected void drawBoxBody(PGraphics g, Body body, Box box) {
        Vector2f[] pts = box.getPoints(body.getPosition(), body.getRotation());

        Vector2f v1 = pts[0];
        Vector2f v2 = pts[1];
        Vector2f v3 = pts[2];
        Vector2f v4 = pts[3];
		
        g.quad(v1.x, v1.y, v2.x, v2.y, v3.x, v3.y, v4.x, v4.y);
    }



}
import javax.swing.JPanel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.awt.Graphics2D;
import java.util.Random;


public class GamePanel extends JPanel implements Runnable{
    
    private Image background;
    private Image space;
    private BufferedImage image;
    private boolean isRunning = false;
    private Thread thread;
    private int pos = 0;
    private int gap = 20;
    private int bdx = 10; 
    private int bWidth = 700;
    private int bHeight = 400;
    private int progress = 0;
    private Wall pair;
    private Hero hero;
    private LinkedList<Orb> orbs = new LinkedList<>();
    private LinkedList<Orb> nextOrbs = new LinkedList<>();
    private HashMap<OrbAnimation, Orb> collection = new HashMap<>();
    private Integer scoreCount = 0;
    private SoundManager soundManager;
	private OrbAnimation orbAnimation;
    private LoseAnimation loseAnimation;



    public GamePanel() {
        background = ImageManager.get_image("images/sky.jpg");
        space = ImageManager.get_image("images/white.jpg");
        image = new BufferedImage (700, 400, BufferedImage.TYPE_INT_RGB);
        soundManager = SoundManager.getInstance();

    }

    public void startGame(){
        if(isRunning){
            return;
        }
        scoreCount = 0;
        GameWindow.score.setText(scoreCount.toString());
        pos=0;
        createEntities();
        orbAnimation.start();
        soundManager.playClip("game", true);
        if(!GameWindow.muted){
            soundManager.setVolume("game", 1.0f);
        }
        else{
            soundManager.setVolume("game", 0.0f);
        }
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    public void createEntities(){
        this.pair = new Wall(this);
        this.hero = new Hero(30, 30);
        pair.create_pair();

        Random rand = new Random();
        for(int i=0; i < rand.nextInt(3, 8); i++){
            Orb orb = new Orb(this, 0);
            orbs.add(orb);
        }
        for(int i=0; i < rand.nextInt(3, 8); i++){
            Orb orb = new Orb(this, bWidth + gap);
            nextOrbs.add(orb);
        }
        orbAnimation = new OrbAnimation();
        loseAnimation = new LoseAnimation();

    }

    public void drawEntities(){
        Graphics2D imageContext = (Graphics2D) image.getGraphics();
		imageContext.drawImage(background, pos*-1, 0, null);
        imageContext.drawImage(space, bWidth - pos, 0 , gap, bHeight, null);
        imageContext.drawImage(background, (bWidth + gap) - pos, 0, null);
            
        moveObjects(imageContext);
        hero.draw(imageContext);
        animateOrbs(imageContext);

		Graphics2D g2 = (Graphics2D) getGraphics();
		g2.drawImage(image, 0, 0, bWidth, bHeight, null);
        pos+= bdx;
        
        if(pos >= bWidth + gap){

            progress++;
            pos -= (bWidth + gap);
            shiftOrb(nextOrbs);
            this.orbs = new LinkedList<>(nextOrbs);
            collection.clear();
            nextOrbs.clear();
            Random rand = new Random();
            for(int i=0; i < rand.nextInt(3, 7); i++){
                Orb orb = new Orb(this, bWidth + gap);
                nextOrbs.add(orb);
            }
            pair.create_pair();
            if((bdx < 25 && progress%5==0)){
                bdx+=5;
                soundManager.playClip("levelUp", false);
            }
        }

		imageContext.dispose();
		g2.dispose();
    }


    /**
     * Move the hero in a given direction
     * @param direction
     */
    public void moveHero(String direction){
        enforceBoundaries();
        hero.move(direction);
        unlock();
    }

    /*
     * Move all objects except for hero
     */
    public void moveObjects(Graphics2D imageContext){
        pair.update_pair(imageContext, bWidth - pos);
        
        for(Orb orb : orbs){
            orb.update_orb(pos, imageContext);
        }

        if(pos >= (bWidth + gap)/2){
            for(Orb orb : nextOrbs){
                orb.update_orb(pos, imageContext);
            }
        }
    }

    /**
     * Ensures that the hero remains within the game panel but setting locks for movement
     */
    public void enforceBoundaries(){
        int heroY = hero.getPos().get('y');
        if(heroY <= 0){
            hero.setLockUp(true);
        }
        if (heroY + hero.getHeight() >= bHeight){
            hero.setLockDown(true);
        }
    }

    /**
     * monitors the positions of the wall pair, orbs and hero and handles a collision if neccessary
     */
    public void moniterPositions(){
        int heroX = hero.getPos().get('x');
        int heroY = hero.getPos().get('y');
        HashMap<String, Integer> wall1 = new HashMap<>(pair.getDimensions().get(0));
        HashMap<String, Integer> wall2 = new HashMap<>(pair.getDimensions().get(1));
        
        if(heroX  + hero.getWidth() >= wall1.get("x") && heroX <= wall1.get("x") + wall1.get("width")){
            if(heroY + hero.getDy() < wall1.get("y") + wall1.get("height") 
                || heroY + hero.getHeight() - hero.getDy() >= wall2.get("y")){
                
                collisionAction();
            }
        }
        
        checkCollections(heroX, heroY);
    }

    /**
     * Sets the placement of an orb to the current panel view
     */
    public void shiftOrb(LinkedList<Orb> orbs){
        for(Orb orb : orbs){
            orb.setFirstX(orb.getFirstX() - (bWidth + gap));
            
        }
    }

    /**
     * Stops the game and resets to "start of game" configuration
     */
    public void collisionAction(){
        performLoseAnimation();
        progress=0;
        bdx=10;
        orbs.clear();
        nextOrbs.clear();
        collection.clear();
        isRunning = false;
    }


    /**
     * Executes all required animations and sounds upon loss
     */
    public void performLoseAnimation(){

        soundManager.stopClip("game");
        soundManager.playClip("lose", false);
        loseAnimation.start();
        int deathX = hero.getX();
        int deathY = hero.getY();
        for(int i=0; i < loseAnimation.getTotalDuration(); i++){
            Graphics2D imageContext = (Graphics2D) image.getGraphics();
            imageContext.drawImage(background, pos*-1, 0, null);
            imageContext.drawImage(space, bWidth - pos, 0 , gap, bHeight, null);
            imageContext.drawImage(background, (bWidth + gap) - pos, 0, null);
            moveObjects(imageContext);
            loseAnimation.update();
            Gameover gameover = new Gameover();
            if(deathX < pair.getX()){
                loseAnimation.draw(imageContext, pair.getX() - gap, deathY);
                gameover.draw(deathX + bWidth/2, i/6, imageContext);
            }
            else{
                loseAnimation.draw(imageContext, pair.getX(), deathY);
                gameover.draw(deathX + bWidth/2, i/6, imageContext);
            }
            Graphics2D g2 = (Graphics2D) getGraphics();
            g2.drawImage(image, 0, 0, bWidth, bHeight, null);
        }
    }
    /**
     * Resets all directional locks to false
     */
    public void unlock(){
        hero.setLockUp(false);
        hero.setLockDown(false);
    }


    /**
     * Collects an orb if touched, adds it to the Map of collected orbs and removes it from the list of current orbs Also updates the score and initiates the animation
     * @param heroX
     * @param heroY
     */
    public void checkCollections(int heroX, int heroY){
        Orb removed = null;
        for(Orb orb : orbs){
            if(orb.getX() >= heroX && orb.getX() <= heroX + hero.getWidth()
                && orb.getY() >= heroY && orb.getY() <= heroY + hero.getHeight()){
                scoreCount++;
                GameWindow.score.setText(scoreCount.toString());
                soundManager.playClip("orbHit", false);
                removed = orb;
                OrbAnimation orbAnimation = new OrbAnimation();
                collection.put(orbAnimation, removed);
                orbAnimation.start();
            }
        }
        orbs.remove(removed);
    }


/**
 * Handles the animation of orbs that have been collected
 * @param imageContext
 */
    public void animateOrbs(Graphics2D imageContext){
        for(Map.Entry<OrbAnimation, Orb> entry : collection.entrySet()){
            OrbAnimation orbAnimation = entry.getKey();
            Orb orb = entry.getValue();
            orbAnimation.update();
            orbAnimation.draw(imageContext, orb.getFirstX() - pos, orb.getY());
        }
    }

    @Override
    public void run() {
        while(isRunning){
            try {
                drawEntities();
                moniterPositions();
                Thread.sleep(80);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

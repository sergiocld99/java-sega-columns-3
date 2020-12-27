package cs10.apps.columns.model;

import cs10.apps.columns.core.Board;

public class FallingBlock extends Block {
    private final FallingPosition position;
    private final Board referencedBoard;
    private MagicStone magicStone;
    private AutoMoveParameters parameters;
    private boolean lose;

    private boolean cpu;
    private int offset = 30;

    public FallingBlock(Block block, Board referencedBoard, boolean cpu, boolean magicStone){
        super(block.getBall1(), block.getBall2(), block.getBall3());
        if (magicStone) setMagicStone(new MagicStone());
        this.referencedBoard = referencedBoard;
        this.cpu = cpu;
        position = new FallingPosition(2,-1);
    }

    public void setParameters(AutoMoveParameters parameters) {
        this.parameters = parameters;
    }

    public void rotate() {
        if (magicStone != null) {
            System.out.println("Target Rotations: " + parameters.getTargetRotations());
            magicStone.rotate();
        } else {
            Ball[] result = new Ball[3];
            result[0] = balls[2];
            result[1] = balls[0];
            result[2] = balls[1];
            balls = result;
        }
    }

    public FallingPosition getPosition() {
        return position;
    }

    public void moveLeft(){
        int x = position.getX();
        double y = position.getY();
        if (x > 0 && referencedBoard.isEmpty(x-1,(int) Math.ceil(y))){
            position.changeX(-1);
        }
    }

    public void moveRight(){
        int x = position.getX();
        double y = position.getY();
        if (x < Board.MAX_X && referencedBoard.isEmpty(x+1,(int) Math.ceil(y))) {
            position.changeX(1);
        }
    }

    private boolean moveDownAux(){
        int x = position.getX();
        double y = position.getY();

        if (y < Board.MAX_Y - referencedBoard.getLines() - 1 && referencedBoard.isEmpty(x,(int) y+1)) {
            if (cpu) autoMoveCPU();
            else position.changeY(0.5);
            return true;
        } else {
            position.setY((int) y);
            return false;
        }
    }

    private void autoMoveCPU(){
        if (offset-- % 10 == 0) position.changeY(0.5);
        if (offset < 0) offset = 19;

        if (position.getX() > parameters.getTargetColumn()){
            if (offset == 4 || offset == 9) moveLeft();
            return;
        } else if (position.getX() < parameters.getTargetColumn()){
            if (offset == 4 || offset == 9) moveRight();
            return;
        }

        if (parameters.isAutoRotation()){

            if (parameters.getTargetRotations() > 0){
                if (offset % 5 == 4) {
                    parameters.decreaseTargetRotations();
                    rotate();
                    parameters.setAutoRotation(parameters.getTargetRotations() > 0);
                }
                return;
            }

            if (parameters.isRotateTwoColorsDown()){
                if (differentColor(1, 2)) {
                    if (offset % 5 == 4) {
                        rotate();
                    }
                }
                else parameters.setAutoRotation(false);
            } else {
                if (differentColor(0, 1)) {
                    if (offset % 5 == 4) {
                        rotate();
                    }
                }
                else parameters.setAutoRotation(false);
            }

            return;
        }

        if (offset == 0) {
            double y = position.getY();
            if (y % 1 < 0.5) position.setY((int) y + 0.5);
            else position.setY((int) y + 1);
            cpu = false;
        }
    }

    public boolean moveDown(boolean voluntary){
        if (voluntary && referencedBoard.getExplosionHelper().isRunning())
            return false;

        boolean result = moveDownAux();

        if (!result){
            int y = (int) position.getY();
            int x = position.getX();

            if (y < 0){
                lose = true;
                return false;
            }

            // MAGIC STONE ACTION
            if (magicStone != null){
                referencedBoard.applyMagicStone(magicStone.getItem(3), x, y+1);
                return false;
            }


            referencedBoard.set(x, y, getBall3());

            if (y-1 < 0){
                lose = true;
                return false;
            }

            referencedBoard.set(x, y-1, getBall2());

            if (y-2 < 0){
                lose = true;
                return false;
            }

            referencedBoard.set(x, y-2, getBall1());
            referencedBoard.incrementHeight(x,3);

            if (!voluntary){
                Position[] positions = new Position[3];
                positions[0] = new Position(x,y-2);
                positions[1] = new Position(x, y-1);
                positions[2] = new Position(x, y);
                referencedBoard.getExplosionHelper().explode(3, 1, positions);
            }
        }

        return result;
    }

    public boolean isLose() {
        return lose;
    }

    public MagicStone getMagicStone() {
        return magicStone;
    }

    public void setMagicStone(MagicStone magicStone) {
        this.magicStone = magicStone;
    }
}

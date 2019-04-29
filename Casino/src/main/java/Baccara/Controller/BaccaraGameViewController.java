/*
 * This peace of Software has been written by Nick Flückiger
 * You are free to use and modifiy this software to your needs
 * For information and contact with the developer please write to
 * Mail: nick.flueckiger@outlook.de
 */
package Baccara.Controller;

import Baccara.Entity.BaccaraCard;
import Baccara.Model.BaccaraGameModel;
import com.team1.casino.User.Util.UserCentral;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Nick Flückiger
 */
public class BaccaraGameViewController implements Initializable, Observer {

    @FXML
    private AnchorPane anchro;
    @FXML
    private ImageView firstLeftCard;
    @FXML
    private HBox playerSide;
    @FXML
    private ImageView thirdLeftCard;
    @FXML
    private ImageView secondLeftCard;
    @FXML
    private HBox dealerSide;
    @FXML
    private ImageView startGameView;
    @FXML
    private ImageView firstRightCard;
    @FXML
    private ImageView secondRightCard;
    @FXML
    private Text playerCardCountLabel;
    @FXML
    private Text dealerCardCountLabel;
    @FXML
    private ImageView chipImage;

    private Dragboard content;
    @FXML
    private ImageView bankerBet;
    @FXML
    private ImageView tieBet;
    @FXML
    private ImageView playerBet;
    @FXML
    private ImageView thirdRightImage;
    @FXML
    private ImageView bankerBetCoin;
    @FXML
    private ImageView winnerCard;
    @FXML
    private Text userBalance;
    @FXML
    private Text totalBet;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCardBacks();
        /*
            Code below checks for drag and drop and sets image at new position.
         */
        this.chipImage.setOnDragDetected((event) -> {
            content = chipImage.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent data = new ClipboardContent();
            Image image = chipImage.getImage();
            data.putImage(image);
            content.setContent(data);
            event.consume();
            // updateBalanceAndBet();
        });
        this.bankerBet.setOnMouseClicked(event -> {
            this.bankerBetCoin.setImage(this.chipImage.getImage());
            this.gameModel.setDealerBet(5);
        });
    }
    private BaccaraGameModel gameModel;

    public void setBaccaraGameModel(BaccaraGameModel gameModel) {
        this.gameModel = gameModel;
        bind();
    }

    private void setCardBacks() {
        ImageView[] imageViews = {this.firstLeftCard, this.secondLeftCard, this.firstRightCard, this.secondRightCard};
        for (ImageView imageView : imageViews) {
            imageView.setImage(new Image("/images/GameCards/cardBack.png"));
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        setCardBacks();
    }

    private void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;
            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();
            double reducCoeff = 0;
            if (ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }

    public void bind() {
    }

    private void updateBalanceAndBet() {
        this.userBalance.setText("Kontostand: " + UserCentral.getInstance().getUser().getCurrentBalance());
        this.totalBet.setText("Einsatz: " + this.gameModel.getTotalBet());
    }

    @FXML
    private void startBaccara(MouseEvent event) throws InterruptedException {
        this.gameModel.generateCards();
        this.gameModel.resetGame();
        resetImageViews();
        //();
        String format = "/images/GameCards/%s";
        ImageView[] playerView = {this.firstLeftCard, this.secondLeftCard};
        ImageView[] dealerView = {this.firstRightCard, this.secondRightCard};
        setCardCount();
        rotateCards(playerView, dealerView, format);
    }

    private void checkForNewCards(String linkFormat) {
        this.gameModel.checkForCardDraw();
        ArrayList<BaccaraCard> playerCards = this.gameModel.getPlayerCards();
        ArrayList<BaccaraCard> dealerCards = this.gameModel.getDealerCards();
        /*
            Check if a third card has been drawn by the algorithm following the rules of Baccara.
         */
        try {

            if (playerCards.size() == 3 && dealerCards.size() != 3) {
                thirdLeftCard.setImage(new Image(String.format(linkFormat, "cardBack.png")));
                this.thirdLeftCard.setVisible(true);
                TranslateTransition translateTransition = createTranslation(this.thirdRightImage);
                translateTransition.play();
                translateTransition.setOnFinished(hanlder -> {
                    RotateTransition trans = createRotator(this.thirdLeftCard, 0, -90, 750, 1);
                    trans.play();
                    trans.setOnFinished(imageSetter -> {
                        this.thirdLeftCard.setImage(new Image(String.format(linkFormat, playerCards.get(2).getImageLocation())));
                        RotateTransition rotateBack = createRotator(this.thirdLeftCard, -85, 0, 750, 1);
                        rotateBack.play();
                        rotateBack.setOnFinished(imageBack -> {
                            try {
                                Thread.sleep(1000);
                                resetImageViews();
                                setCardBacks();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(BaccaraGameViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    });
                });
            }
            if (dealerCards.size() == 3 && playerCards.size() != 3) {
                this.thirdRightImage.setImage(new Image(String.format(linkFormat, "cardBack.png")));
                this.thirdRightImage.setVisible(true);
                TranslateTransition translateTransition = createTranslation(this.thirdRightImage);
                translateTransition.play();
                translateTransition.setOnFinished(hanlder -> {
                    RotateTransition trans = createRotator(this.thirdRightImage, 0, -90, 750, 1);
                    trans.play();
                    trans.setOnFinished(imageSetter -> {
                        this.thirdRightImage.setImage(new Image(String.format(linkFormat, dealerCards.get(2).getImageLocation())));
                        RotateTransition rotate = createRotator(this.thirdRightImage, -85, 0, 750, 1);
                        rotate.play();
                        rotate.setOnFinished(cardBack -> {
                            try {
                                Thread.sleep(1000);
                                resetImageViews();
                                setCardBacks();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(BaccaraGameViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    });
                });
            }

            if (dealerCards.size() == 3 && playerCards.size() == 3) {
                ImageView[] imageViews = {this.thirdLeftCard, this.thirdRightImage};
                for (ImageView view : imageViews) {
                    try {
                        view.setImage(new Image(String.format(linkFormat, "cardBack.png")));
                        view.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                TranslateTransition left = createTranslation(this.thirdLeftCard);
                TranslateTransition right = createTranslation(this.thirdRightImage);
                ParallelTransition parTrans = new ParallelTransition(left, right);
                parTrans.play();
                parTrans.setOnFinished(parFinished -> {
                    ParallelTransition parRotate = new ParallelTransition(createRotator(this.thirdLeftCard, 0, -89, 750, 1), createRotator(this.thirdRightImage, 0, -89, 750, 1));
                    parRotate.play();
                    parRotate.setOnFinished(rotationFinished -> {
                        this.thirdRightImage.setImage(new Image(String.format(linkFormat, dealerCards.get(2).getImageLocation())));
                        this.thirdLeftCard.setImage(new Image(String.format(linkFormat, playerCards.get(2).getImageLocation())));
                        ParallelTransition returnBack = new ParallelTransition(createRotator(this.thirdLeftCard, -85, 0, 750, 1), createRotator(this.thirdRightImage, -85, 0, 750, 1));
                        returnBack.play();
                        returnBack.setOnFinished(backRotationFiniseh -> {
                            try {
                                Thread.sleep(1000);
                                resetImageViews();
                                setCardBacks();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(BaccaraGameViewController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    });
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCardCount();
    }

    private TranslateTransition createTranslation(ImageView view) {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(1500));
        translateTransition.setNode(view);
        translateTransition.fromYProperty().set(view.getY() + ((241 - (241 * 2)) * 2));
        translateTransition.toYProperty().setValue(view.getY());
        return translateTransition;
    }

    /**
     * Rotate inner and outer cards in paralell and check if a third card is
     * needed for player and/or dealer.
     *
     * @param playerView
     * @param dealerView
     * @param format
     */
    private void rotateCards(ImageView[] playerView, ImageView[] dealerView, String format) {
        ImageView[][] views = {playerView, dealerView};
        RotateTransition[] rotators = new RotateTransition[2];
        int index = 0;
        rotators[0] = createRotator(playerView[0], 0, -90, 500, 1);
        rotators[1] = createRotator(dealerView[0], 0, -90, 500, 1);
        ParallelTransition rotateOuterFirst = new ParallelTransition(rotators);
        rotateOuterFirst.play();
        rotateOuterFirst.setOnFinished((ActionEvent event) -> {
            flipCardsArround(format, playerView[0], dealerView[0], 0);
            ParallelTransition rotateCards = new ParallelTransition(createRotator(playerView[0], -85, 0, 500, 1), createRotator(dealerView[0], -85, 0, 500, 1));
            rotateCards.play();
            rotateCards.setOnFinished(rotationFinished -> {
                ParallelTransition rotateOuterCards = new ParallelTransition(createRotator(playerView[1], 0, -90, 500, 1), createRotator(dealerView[1], 0, -90, 500, 1));
                rotateOuterCards.play();
                rotateOuterCards.setOnFinished(outerCardsRotaded -> {
                    flipCardsArround(format, playerView[1], dealerView[1], 1);
                    ParallelTransition rotateOuterSecond = new ParallelTransition(createRotator(playerView[1], -85, 0, 500, 1), createRotator(dealerView[1], -85, 0, 500, 1));
                    rotateOuterSecond.play();
                    rotateOuterSecond.setOnFinished(secondRotationEnd -> {
                        checkForNewCards(format);
                    });
                });
            });
        });
    }

    private RotateTransition createRotator(Node card, int startAngle, int endAngle, int duration, int cicle) {
        RotateTransition rotator = new RotateTransition(Duration.millis(duration), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(startAngle);
        rotator.setToAngle(endAngle);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);
        return rotator;
    }

    private void flipCardsArround(String format, ImageView playerView, ImageView dealerView, int pos) {
        //set cards
        ArrayList<BaccaraCard> playerCards = this.gameModel.getPlayerCards();
        ArrayList<BaccaraCard> dealerCard = this.gameModel.getDealerCards();
        try {
            Image playerImage = new Image(String.format(format, playerCards.get(pos).getImageLocation()));
            Image dealerImage = new Image(String.format(format, dealerCard.get(pos).getImageLocation()));
            playerView.setImage(playerImage);
            dealerView.setImage(dealerImage);
        } catch (Exception e) {
            System.out.println(String.format(format, playerCards.get(pos).getImageLocation()));
            System.out.println(String.format(format, dealerCard.get(pos).getImageLocation()));
            e.printStackTrace();
        }
    }

    private void resetImageViews() {
        this.bankerBetCoin.setImage(null);
        ImageView[] imageViews = {this.firstLeftCard, this.secondLeftCard, this.thirdLeftCard, this.firstRightCard, this.secondRightCard, this.thirdRightImage};
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView();
        }
        this.thirdLeftCard.setVisible(false);
        this.thirdLeftCard.setImage(null);
        this.thirdRightImage.setVisible(false);
        this.thirdRightImage.setImage(null);
    }

    private void setCardCount() {
        //Update the card count for player and dealer by the gameModel values.
        int playerCardCount = this.gameModel.getPlayerCardCount();
        int dealerCardCount = this.gameModel.getDealerCardCount();
        this.playerCardCountLabel.setText("Spieler Kartenwert: " + String.valueOf(playerCardCount));
        this.dealerCardCountLabel.setText("Dealer Kartenwert: " + String.valueOf(dealerCardCount));
    }
    private static int betAmont = 0;

    @FXML
    private void setBankerBet(DragEvent event) {
        ImageView chipContainer = new ImageView(this.content.getImage());
        chipContainer.localToParent(this.bankerBet.getX(), this.bankerBet.getY());
        chipContainer.setVisible(true);
    }

    @FXML
    private void setTieBet(DragEvent event) {

    }

    @FXML
    private void setPlayerBet(DragEvent event) {
    }

    @FXML
    private void dropCoin(MouseDragEvent event) {
        System.out.println(this.bankerBet.getX());
        System.out.println(this.bankerBet.getY());
    }

}

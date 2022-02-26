package test;

import data.DataHelper;
import lombok.val;
import lombok.var;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");

        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();

        var balance1 = dashboardPage.getCardBalance(0);
        var balance2 = dashboardPage.getCardBalance(1);

        if (balance1 > balance2) {
            int transferSum = (balance1 - balance2) / 2;
            var cardInfo = DataHelper.getFirstCardInfo();
            var replenishmentPage = dashboardPage.secondButton();
            replenishmentPage.transferMoney(cardInfo, transferSum);
        }
        if (balance1 < balance2) {
            int transferSum = (balance2 - balance1) / 2;
            var cardInfo = DataHelper.getSecondCardInfo();
            var replenishmentPage = dashboardPage.firstButton();
            replenishmentPage.transferMoney(cardInfo, transferSum);
        }
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        int amount = 500;
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var replenishmentPage = dashboardPage.secondButton();
        var cardInfo = DataHelper.getFirstCardInfo();
        replenishmentPage.transferMoney(cardInfo, amount);
        int balanceAfterOnCardFrom = DataHelper.balanceFrom(balanceSecondBefore, amount);
        int balanceAfterOnCardTo = DataHelper.balanceTo(balanceFirstBefore, amount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);
        assertEquals(balanceSecondAfter, balanceAfterOnCardTo);
        assertEquals(balanceFirstAfter, balanceAfterOnCardFrom);


    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        int amount = 1000;
        var dashboardPage = new DashboardPage();
        var balanceFirstBefore = dashboardPage.getCardBalance(0);
        var balanceSecondBefore = dashboardPage.getCardBalance(1);
        var replenishmentPage = dashboardPage.firstButton();
        var cardInfo = DataHelper.getSecondCardInfo();
        replenishmentPage.transferMoney(cardInfo, amount);
        int balanceAfterOnCardFrom = DataHelper.balanceFrom(balanceFirstBefore, amount);
        int balanceAfterOnCardTo = DataHelper.balanceTo(balanceSecondBefore, amount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);

        assertEquals(balanceFirstAfter, balanceAfterOnCardTo);
        assertEquals(balanceSecondAfter, balanceAfterOnCardFrom);
    }



}
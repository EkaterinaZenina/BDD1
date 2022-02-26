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

        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        val dashboardPage = new DashboardPage();

        val balance1 = dashboardPage.getCardBalance(0);
        val balance2 = dashboardPage.getCardBalance(1);

        if (balance1 > balance2) {
            int transferSum = (balance1 - balance2) / 2;
            val cardInfo = DataHelper.getFirstCardInfo();
            val replenishmentPage = dashboardPage.secondButton();
            replenishmentPage.transferMoney(cardInfo, transferSum);
        }
        if (balance1 < balance2) {
            int transferSum = (balance2 - balance1) / 2;
            val cardInfo = DataHelper.getSecondCardInfo();
            val replenishmentPage = dashboardPage.firstButton();
            replenishmentPage.transferMoney(cardInfo, transferSum);
        }
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondCard() {
        int amount = 500;
        val dashboardPage = new DashboardPage();
        val balanceFirstBefore = dashboardPage.getCardBalance(0);
        val balanceSecondBefore = dashboardPage.getCardBalance(1);
        val replenishmentPage = dashboardPage.secondButton();
        val cardInfo = DataHelper.getFirstCardInfo();
        replenishmentPage.transferMoney(cardInfo, amount);
        int balanceAfterFrom = DataHelper.balanceFrom(balanceSecondBefore, amount);
        int balanceAfterTo = DataHelper.balanceTo(balanceFirstBefore, amount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);
        assertEquals( balanceFirstAfter,balanceAfterFrom);
        assertEquals(balanceSecondAfter, balanceAfterTo );
    }

    @Test
    void shouldTransferMoneyFromSecondToFirstCard() {
        int amount = 100;
        val dashboardPage = new DashboardPage();
        val balanceFirstBefore = dashboardPage.getCardBalance(0);
        val balanceSecondBefore = dashboardPage.getCardBalance(1);
        val replenishmentPage = dashboardPage.firstButton();
        val cardInfo = DataHelper.getSecondCardInfo();
        replenishmentPage.transferMoney(cardInfo, amount);
        int balanceAfterFrom = DataHelper.balanceFrom(balanceFirstBefore, amount);
        int balanceAfterTo = DataHelper.balanceTo(balanceSecondBefore, amount);
        int balanceFirstAfter = dashboardPage.getCardBalance(0);
        int balanceSecondAfter = dashboardPage.getCardBalance(1);
        assertEquals(balanceFirstAfter, balanceAfterTo);
        assertEquals(balanceSecondAfter, balanceAfterFrom);
    }




}
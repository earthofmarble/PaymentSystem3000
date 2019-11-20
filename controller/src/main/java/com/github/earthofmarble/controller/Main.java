package com.github.earthofmarble.controller;

//import com.github.earthofmarble.configuration.AppConfiguration;
import com.github.earthofmarble.model.dto.account.AccountExtendedDto;
import com.github.earthofmarble.model.dto.account.AccountInfoDto;
import com.github.earthofmarble.model.model.account.Account;
import com.github.earthofmarble.model.model.currency.Currency;
import com.github.earthofmarble.service.api.account.IAccountService;
import com.github.earthofmarble.service.api.payment.IPaymentService;
import com.github.earthofmarble.utility.mapper.service.Mapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * Created by earthofmarble on Oct, 2019
 */

public class Main {

    public static void main(String[] args) {
//
//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
////
////        IUserService userService = context.getBean(IUserService.class);
//        IAccountService accountService = context.getBean(IAccountService.class);
////        IPaymentService paymentService = context.getBean(IPaymentService.class);
////        Mapper mapper = context.getBean(Mapper.class);
////
////        AccountExtendedDto accountDto = (AccountExtendedDto) accountService.readById(1, AccountExtendedDto.class);
////        AccountExtendedDto accountDto2 = (AccountExtendedDto) accountService.readById(2, AccountExtendedDto.class);
////        paymentService.createPayment((Account) mapper.convert(accountDto, Account.class, null), (Account) mapper.convert(accountDto2, Account.class, null), 10.0);
////
//
//
//        accountService.orderMoney("1112", "1111", 10.0);

//        UserFilter uf = new UserFilter();
//        uf.setFirstName("");
//        uf.setFirstElement(0);
//        uf.setPageSize(1);
//        System.err.println(userService.readWithFilter(uf, UserInfoDto.class));
//
//        UserProfileDto userProfileDto = new UserProfileDto();
//        UserCredsDto userCredsDto = new UserCredsDto();
//
//        userCredsDto.setUsername("NNN3");
//        userCredsDto.setPassword("NNNN33");
//
//        userProfileDto.setFirstName("n3");
//        userProfileDto.setLastName("n333");
//        userProfileDto.setRole(UserRole.ROLE_ADMIN);
//        userProfileDto.setEmail("earthofmarble@gmail.com");
//        userProfileDto.setUserCreds(userCredsDto);
//
//        UserInfoDto userInfoDto = new UserInfoDto();
//        userInfoDto.setId(3);
//
////        userService.create(userProfileDto);
//
//
//        accountService.orderMoney("1111", "1112", 12.0);



//        RestTemplate restTemplate = new RestTemplate();
//        BankCurrencyDto response = restTemplate.getForObject("http://www.nbrb.by/api/exrates/rates/292", BankCurrencyDto.class);
//        System.out.println(response);
//
//        Mapper mapper = new Mapper();
//        CurrencyDto currencyDto = (CurrencyDto) mapper.convert(response, CurrencyDto.class, null);
//        System.err.println(currencyDto);
//        System.out.println(mapper.convert(currencyDto, Currency.class, null));




    }

}

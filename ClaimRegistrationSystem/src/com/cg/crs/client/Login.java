package com.cg.crs.client;

import java.util.Scanner;

import com.cg.crs.entity.UserRole;
import com.cg.crs.exception.CRSException;
import com.cg.crs.service.CRSService;
import com.cg.crs.service.CRSServiceImpl;

public class Login {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CRSService crsService = new CRSServiceImpl();
		boolean flag1 = false;
		do {

			System.out.println("Welcome");
			Scanner scanner = new Scanner(System.in);
			System.out.println("enter username");
			String userName = scanner.nextLine();
			System.out.println("Enter password");
			String password = scanner.nextLine();
			UserRole user = new UserRole();
			user.setUserName(userName);
			user.setPassword(password);
			boolean services;
			try {
				services = crsService.validateFields(user);
				if (services == true) {
					switch (user.getRoleCode()) {
					case "INSURED":
						ClaimCreation.main(args);
						flag1 = true;
						break;
					case "AGENT":
						flag1 = true;
						break;
					case "ADMIN":
						UserProfileCreation.profileCreation(user);
						flag1 = true;
						break;
					default:
						break;
					}

				} else {

					System.out.println("Enter Valid Username/Password");
					flag1 = false;
				}
			} catch (CRSException e) {
				// TODO Auto-generated catch block
				flag1 = false;
				System.err.println(" " + e.getMessage());
			}

		} while (!flag1);
	}
}
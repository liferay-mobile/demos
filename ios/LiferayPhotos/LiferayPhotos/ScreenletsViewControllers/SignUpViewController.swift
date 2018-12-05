/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import UIKit
import LiferayScreens
class SignUpViewController: UIViewController, SignUpScreenletDelegate {
    @IBOutlet weak var screenlet: SignUpScreenlet!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        screenlet.delegate = self
        screenlet.anonymousApiUserName = "anonymous1@liferay.com"
        screenlet.anonymousApiPassword = "anonymous1"
    }
    
    //MARK: SignUpScreenletDelegate
    
    func screenlet(_ screenlet: SignUpScreenlet, onSignUpResponseUserAttributes attributes: [String: AnyObject]) {
        let nextVC = MainViewController()
        let navigationController = UINavigationController(rootViewController: nextVC)
        self.present(navigationController, animated: true, completion: nil)
    }
    
    func screenlet(_ screenlet: SignUpScreenlet, onSignUpError error: NSError) {
        showAlert(title: "Error")
    }
}

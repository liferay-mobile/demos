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

class ImageGalleryView_liferayphotos_profile: ImageGalleryView_default {

	override var columnNumber: Int {
		get {
			return 3
		}
		set {}
	}
    
    var allImages = [String:[ImageEntry]]()
    var userImages = [String:[ImageEntry]]()
    
    override func doCreateLayout() -> UICollectionViewLayout {
        let layout = UICollectionViewFlowLayout()
        
        let cellWidth = UIScreen.main.bounds.width / CGFloat(columnNumber) - spacing
        
        layout.sectionInset = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 0)
        layout.itemSize = CGSize(width: cellWidth, height: cellWidth)
        layout.minimumInteritemSpacing = spacing
        layout.minimumLineSpacing = spacing
        
        return layout
    }

    private func isPhotoOfUser(_ image: ImageEntry) -> Bool {
        let imageUserId = (image.attributes["userId"]! as! NSString).integerValue
        let userId = SessionContext.currentContext!.user.userId
        return imageUserId == userId
    }
    
    override func setRows(_ allRows: [String : [AnyObject?]], newRows: [String : [AnyObject]], rowCount: Int, sections: [String]) {
        let defaultSection = allRows[BaseListView.DefaultSection] as! [ImageEntry?]
        let userImageEntries = defaultSection.compactMap{ $0 }.filter(isPhotoOfUser)
        let userImageRows = [BaseListView.DefaultSection:userImageEntries]
        super.setRows(userImageRows, newRows: userImageRows, rowCount: userImageEntries.count, sections: sections)
    }
}

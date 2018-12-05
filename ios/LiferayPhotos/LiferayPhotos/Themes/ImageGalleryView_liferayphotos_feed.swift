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

class ImageGalleryView_liferayphotos_feed: ImageGalleryView_default {
    
    let numberOfColumns = 1
	let cellId = "feedCell"
    
    override var columnNumber: Int {
        get {
            return numberOfColumns
        }
        set {}
    }

	override func onCreated() {
		super.onCreated()

		let grayColor = UIColor(red: 230/255.0, green: 230/255.0, blue: 230/255.0, alpha: 1)

		backgroundColor = grayColor
		collectionView?.backgroundColor = grayColor
	}


	override func doGetCellId(indexPath: IndexPath, object: AnyObject?) -> String {
		return cellId
	}

	override func doRegisterCellNibs() {
		let uiNib = UINib(nibName: "FeedCell", bundle: nil)

		collectionView?.register(uiNib, forCellWithReuseIdentifier: cellId)
	}

	override func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: IndexPath) -> CGSize {

		let width = collectionView.bounds.width

		return CGSize(width: width - 20, height: 400)
	}

	override func doCreateLayout() -> UICollectionViewLayout {
		let layout = super.doCreateLayout() as! UICollectionViewFlowLayout

		layout.minimumLineSpacing = 7.0

		return layout
	}

	override func doFillLoadedCell(indexPath: IndexPath, cell: UICollectionViewCell, object: AnyObject) {
		let imageEntry = object as! ImageEntry
		let cell = cell as! FeedCell

		cell.render(imageEntry: imageEntry)
	}
}

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

import Foundation
import LiferayScreens

class CommentListView_liferayphotos: CommentListView_default {
    
    let cellId = "CommentTableViewCell_liferayphotos"
    
    override func doGetCellId(row: Int, object: AnyObject?) -> String {
        return cellId
    }
    
    override func doRegisterCellNibs() {
        let uiNib = UINib(nibName: cellId, bundle: nil)
        tableView?.register(uiNib, forCellReuseIdentifier: cellId)
    }
    
    override func tableView(_ tableView: UITableView, heightForRowAtIndexPath indexPath: IndexPath)
        -> CGFloat {
            let comment = rows[BaseListView.DefaultSection]?[indexPath.row] as? Comment
            return CommentDisplayView_liferayphotos.heightForText(comment?.htmlBody,
                                                            width: tableView.frame.width)
    }

}

package com.mibody.app.utilities;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public interface ItemTouchHelperViewHolder {
    /**
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSelected();


    /**
     * state should be cleared.
     */
    void onItemClear();
}
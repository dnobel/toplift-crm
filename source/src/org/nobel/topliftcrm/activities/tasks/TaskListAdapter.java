package org.nobel.topliftcrm.activities.tasks;

import java.util.List;

import org.nobel.highriseapi.entities.Task;
import org.nobel.highriseapi.resources.TaskResource.TaskList;
import org.nobel.topliftcrm.R;
import org.nobel.topliftcrm.activities.base.EntityListAdapter;

import android.graphics.Paint;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class TaskListAdapter extends EntityListAdapter<Task> {

    private final TaskListActivity activity;

    public TaskListAdapter(TaskListActivity context, List<Task> tasks) {
        super(context, tasks);
        this.activity = context;
    }

    @Override
    public int getListItemLayoutId() {
        return R.layout.task_list_item;
    }

    @Override
    public void renderListItem(View taskListItem, Task task) {
		TextView taskBodyText = (TextView) taskListItem.findViewById(R.id.taskBody);
		CheckBox taskCheckBox = (CheckBox) taskListItem.findViewById(R.id.taskCheckbox);
        taskBodyText.setText(task.getBody());

        if (activity.getSelectedTaskList() == TaskList.COMPLETED) {
            taskBodyText.setPaintFlags(taskBodyText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			taskCheckBox.setChecked(true);
		} else {
			taskCheckBox.setChecked(false);
        }

        TextView taskFrameText = (TextView) taskListItem.findViewById(R.id.taskFrame);
        taskFrameText.setText(taskFrameText.getText() + task.getFrame());
    }
}

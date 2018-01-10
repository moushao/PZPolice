package com.pvirtech.pzpolice.ui.presenter;

import com.pvirtech.pzpolice.ui.contract.TeamAssessmentActivityContract;
import com.pvirtech.pzpolice.ui.model.TeamAssessmentActivityModelImpl;

/**
 * Created by Administrator on 2017/03/22
 */

public class TeamAssessmentActivityPresenterImpl implements TeamAssessmentActivityContract.Presenter {
    TeamAssessmentActivityModelImpl model = new TeamAssessmentActivityModelImpl();
    TeamAssessmentActivityContract.View view;

    public TeamAssessmentActivityPresenterImpl(TeamAssessmentActivityContract.View view) {
        this.view = view;
    }
}
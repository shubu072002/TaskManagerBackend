package com.projmgr.dashboard.service;

import com.projmgr.dashboard.dto.DashboardResponse;

public interface DashboardService {
    DashboardResponse forUser(String email);
}

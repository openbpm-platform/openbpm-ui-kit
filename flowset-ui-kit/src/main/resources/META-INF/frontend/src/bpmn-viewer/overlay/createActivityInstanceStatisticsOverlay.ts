/*
 * Copyright (c) Haulmont 2026. All Rights Reserved.
 * Use is subject to license terms.
 */

import {ActivityInstanceStatisticsOverlayData} from "./types";
import {OverlayAttrs} from "diagram-js/lib/features/overlays/Overlays";

// shared by the active-instance and incident badges: at badge size (<10px) only a solid
// dot stays legible, so the badge color alone tells the two states apart
const DOT_ICON = `<svg viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
    <circle cx="8" cy="8" r="5" fill="currentColor"/></svg>`;

const COMPLETED_ICON = `<svg viewBox="0 0 16 16" xmlns="http://www.w3.org/2000/svg">
    <path d="M3 8.5 6.5 12 13 4.5" fill="none" stroke="currentColor" stroke-width="2.2"
          stroke-linecap="round" stroke-linejoin="round"/></svg>`;

const statisticsBadge = (className: string, icon: string, count?: string, tooltipMessage?: string): string =>
    count
        ? `<span class="${className}" title="${tooltipMessage}"><span class="badge-icon">${icon}</span>${count}</span>`
        : '';

// first line: active token and incident counts; second line: completed activity instance count
export const createActivityInstanceStatisticsOverlay = (data: ActivityInstanceStatisticsOverlayData): OverlayAttrs => {
    const stateLine = statisticsBadge('active-tokens-overlay', DOT_ICON,
            data.activeCount, data.activeCountTooltipMessage)
        + statisticsBadge('incident-overlay', DOT_ICON,
            data.incidentCount, data.incidentCountTooltipMessage);
    const completedLine = statisticsBadge('completed-activity-overlay', COMPLETED_ICON,
        data.completedCount, data.completedCountTooltipMessage);

    return {
        html: `<div class="activity-instance-statistics-overlay">
                   ${stateLine ? `<div class="statistics-line">${stateLine}</div>` : ''}
                   ${completedLine ? `<div class="statistics-line statistics-line-completed">${completedLine}</div>` : ''}
                </div>`,
        position: {
            left: -10,
            bottom: 10
        }
    }
};

/*
 * Copyright (c) Haulmont 2025. All Rights Reserved.
 * Use is subject to license terms.
 */

import {ElementLike} from "diagram-js/lib/model/Types";

export enum OverlayType {
    DOCUMENTATION = 'documentation',
    DECISION = 'decision',
    DECISION_INSTANCE = 'decision-instance',
    CALLED_PROCESS = 'called-process',
    CALLED_PROCESS_INSTANCE = 'called-process-instance',
    ACTIVITY_STATISTICS = 'activity-statistics',
    ACTIVITY_INSTANCE_STATISTICS = 'activity-instance-statistics',
    INCIDENT_COUNT = 'incident-count',
    SEND_MESSAGE = 'send-message',
    TRANSACTION_BOUNDARY = 'transaction-boundary',
    VARIABLE_CHANGE = 'variable-change',
    HIGHLIGHT = 'highlight',
}

export interface VariableChangeOverlayCmd {
    elementId: string;
    changes: VariableChangeData[];
}

export interface VariableChangeData {
    ordinalNumber: string;
    tooltipMessage: string;
    changeType: VariableChangeType;
}

export enum VariableChangeType {
    CREATE = 'CREATE',
    UPDATE = 'UPDATE',
    DELETE = 'DELETE'
}

export interface IncidentOverlayData {
    elementId: string;
    incidentCount: number;
    tooltipMessage: string;
}

export interface DecisionInstanceLinkOverlayData {
    activityId?: string;
    decisionInstanceId?: string;
    tooltipMessage?: string;
}

export interface DecisionInstanceLinkOverlayParams {
    data: DecisionInstanceLinkOverlayData;
    handleClick: (decisionInstanceId: string) => void;
}

export interface NewActivityStatisticsOverlayData {
    elementId: string;
    incidentCount?: string;
    instanceCount: string;
    incidentCountTooltipMessage?: string;
    instanceCountTooltipMessage: string;
}

export interface ActivityInstanceStatisticsOverlayData {
    elementId: string;
    activeCount?: string;
    completedCount?: string;
    incidentCount?: string;
    activeCountTooltipMessage?: string;
    completedCountTooltipMessage?: string;
    incidentCountTooltipMessage?: string;
}

export interface ActivityInstanceStatisticsCmdData {
    visible?: boolean;
    elements?: ActivityInstanceStatisticsOverlayData[];
}


export interface OverlayPosition {
    top?: number,
    right?: number,
    bottom?: number,
    left?: number
}

export interface OverlayData {
    htmlContainer: HTMLDivElement
}

export interface DocumentationOverlayData {
    showDocumentationOverlay?: boolean;
}

export interface DocumentationOverlayParams {
    data: DocumentationOverlayData;
    handleClick: (element: ElementLike, documentationValue: string) => void;
}

export interface SendMessageOverlaysData {
    tooltipMessage: string;
    useStartEvents: boolean;
    useActiveEvents: boolean;
}

export interface SendMessageOverlaysParams {
    data: SendMessageOverlaysData;
    handleClick: (details: JSON) => void;
}

export interface CalledInstancesOverlayData {
    elementId: string;
    processInstanceIds?: string[];
    tooltipMessage: string;
}

export interface CalledInstancesOverlayParams {
    data: CalledInstancesOverlayData;
    handleClick: (details: JSON) => void;
}

export interface CalledProcessOverlaysData {
    visible?: boolean;
    tooltipMessage?: string;
}

export interface DecisionLinkOverlaysData {
    visible?: boolean;
    tooltipMessage?: string;
}

export interface CalledProcessOverlaysParams {
    data: CalledProcessOverlaysData;
    handleClick: (element: any, callActivityData: JSON) => void;
}

export interface DecisionLinkOverlaysParams {
    data: DecisionLinkOverlaysData;
    handleClick: (element: any, businessRuleTaskData: JSON) => void;
}


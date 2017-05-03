<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="json" uri="http://www.atg.com/taglibs/json" %>
<json:object>
	<json:object name="tableData">
		<json:property name="range" value="${lstCtrl.range}"/>
		<json:array var="range" items="${lstCtrl.ranges}" name="ranges">
			<json:object>
				<json:property name="key" value="${range.key}"/>
				<json:property name="label" value="${range.label}"/>
			</json:object>
		</json:array>
		<json:property name="sortBy" value="${lstCtrl.sortBy}"/>
		<json:array var="sortBy" items="${lstCtrl.sortBys}" name="sortBys">
			<json:object>
				<json:property name="key" value="${sortBy.key}"/>
				<json:property name="label" value="${sortBy.label}"/>
			</json:object>
		</json:array>
	</json:object>
	<json:array var="inspection" items="${lstCtrl.results}" name="rowData">
		<json:object>
			<json:property name="inspectionId" value="${inspection.inspectionId}"/>
			<json:property name="inspectionDate"><s:date name="#attr.inspection.inspectionDate" format="MM/dd/yyyy"/></json:property>
			<json:property name="cmpDueDate"><s:date name="#attr.inspection.cmpDueDate" format="MM/dd/yyyy"/></json:property>
			<json:property name="cmpAmount" value="${inspection.cmpAmount}"/>
			<json:property name="totalReductions" value="${inspection.totalReductions}"/>
			<json:property name="totalPayments" value="${inspection.totalPayments}"/>
			<json:property name="outstanding" value="${inspection.outstanding}"/>
			<json:array name="reductions" items="${inspection.reductions}" var="reduction">
				<json:object>
					<json:property name="transactionId" value="${reduction.transactionId}"/>
					<json:property name="transactionDate"><s:date name="#attr.reduction.transactionDate" format="MM/dd/yyyy"/></json:property>
					<json:property name="amount" value="${reduction.amount}"/>
					<json:property name="comment" value="${reduction.comment}"/>
				</json:object>
			</json:array>
			<json:array name="payments" items="${inspection.payments}" var="payment">
				<json:object>
					<json:property name="transactionId" value="${payment.transactionId}"/>
					<json:property name="transactionDate"><s:date name="#attr.payment.transactionDate" format="MM/dd/yyyy"/></json:property>
					<json:property name="amount" value="${payment.amount}"/>
					<json:property name="comment" value="${payment.comment}"/>
				</json:object>
			</json:array>
		</json:object>
	</json:array>
</json:object>
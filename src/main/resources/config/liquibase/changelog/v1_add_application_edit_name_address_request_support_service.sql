UPDATE application.lk_support_services
SET created_by_user=NULL, created_date=NULL, modified_by_user=NULL, modified_date=NULL, is_deleted=0, "cost"=0, desc_ar='إتاحة تعديل اسم المالك أو العنوان للعلامة التجارية بناء على طلب المالك', desc_en='Providing the owners name or title for the trademark to be modified upon the owners request', name_ar='تعديل بيانات العلامة', name_en='Edit Trademark Data', code='EDIT_TRADEMARK_NAME_ADDRESS'
WHERE code = 'EDIT_TRADEMARK_NAME_ADDRESS';

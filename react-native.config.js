module.exports = {
    dependency: {
        platforms: {
            android: {
                sourceDir: './android',
                manifestPath: 'java/AndroidManifest.xml',
                packageImportPath: 'import org.opencv.OSDOpenCvPackage;',
                packageInstance: 'new OSDOpenCvPackage()',
            }
        }
    }
};
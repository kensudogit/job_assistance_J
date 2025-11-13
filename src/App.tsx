import React, { useState } from 'react';
import { useTranslation } from 'react-i18next';
import Sidebar from '../components/Sidebar';
import WorkerList from '../components/WorkerList';
import WorkerForm from '../components/WorkerForm';
import ProgressManagement from '../components/ProgressManagement';
import JapaneseProficiencyManagement from '../components/JapaneseProficiencyManagement';
import SkillTrainingManagement from '../components/SkillTrainingManagement';
import IntegratedDashboard from '../components/IntegratedDashboard';
import TrainingMenuManagement from '../components/TrainingMenuManagement';
import MilestoneManagement from '../components/MilestoneManagement';
import CareerPathTimeline from '../components/CareerPathTimeline';
import EvidenceReport from '../components/EvidenceReport';
import AdminSummary from '../components/AdminSummary';
import TrainingSessionDetail from '../components/TrainingSessionDetail';
import TrainingMenuAssignmentComponent from '../components/TrainingMenuAssignment';
import ConstructionSimulatorManagement from '../components/ConstructionSimulatorManagement';
import IntegratedGrowthDashboard from '../components/IntegratedGrowthDashboard';
import SpecificSkillTransitionManagement from '../components/SpecificSkillTransitionManagement';
import CareerGoalManagement from '../components/CareerGoalManagement';
import LanguageSelector from '../components/LanguageSelector';
import '../app/globals.css';

type MenuType = 'dashboard' | 'progress' | 'japanese' | 'skill' | 'milestone' | 'career' | 'report' | 'sessions' | 'assignment' | 'simulator' | 'integrated-growth' | 'specific-skill' | 'career-goal';

function App() {
  const { t } = useTranslation();
  const [selectedWorker, setSelectedWorker] = useState<number | null>(null);
  const [showForm, setShowForm] = useState(false);
  const [activeMenu, setActiveMenu] = useState<MenuType>('dashboard');

  // メニューラベルを取得する関数
  const getMenuLabel = (menu: MenuType): string => {
    const labels: Record<MenuType, string> = {
      'dashboard': t('integratedDashboard'),
      'progress': t('progressManagement'),
      'japanese': t('japaneseProficiency'),
      'skill': t('skillTraining'),
      'milestone': t('milestoneManagement'),
      'career': t('careerPathTimeline'),
      'report': t('evidenceReport'),
      'sessions': t('trainingSessions'),
      'assignment': t('trainingMenuAssignment'),
      'simulator': '建設機械シミュレーター',
      'integrated-growth': '統合成長管理',
      'specific-skill': '特定技能移行',
      'career-goal': 'キャリア目標',
    };
    return labels[menu] || menu;
  };

  return (
    <div className="min-h-screen bg-gray-50 flex">
      {/* サイドバー */}
      <Sidebar activeMenu={activeMenu} onMenuChange={(menu) => setActiveMenu(menu as MenuType)} />

      {/* メインコンテンツエリア */}
      <main className="flex-1 ml-64 relative overflow-hidden">
        {/* 背景装飾 */}
        <div className="fixed inset-0 -z-10">
          <div className="absolute top-0 left-0 w-96 h-96 bg-blue-400 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-pulse-slow"></div>
          <div className="absolute top-0 right-0 w-96 h-96 bg-purple-400 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-pulse-slow" style={{ animationDelay: '2s' }}></div>
          <div className="absolute bottom-0 left-1/2 w-96 h-96 bg-indigo-400 rounded-full mix-blend-multiply filter blur-3xl opacity-20 animate-pulse-slow" style={{ animationDelay: '4s' }}></div>
        </div>

        {/* ヘッダー */}
        <header className="bg-gradient-to-r from-indigo-600 to-purple-600 text-white shadow-lg sticky top-0 z-30">
          <div className="px-6 py-4 flex items-center justify-between">
            <div className="flex items-center gap-4">
              <div className="w-8 h-8 bg-white/20 rounded-lg flex items-center justify-center">
                <svg className="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                </svg>
              </div>
              <h1 className="text-2xl font-bold">{getMenuLabel(activeMenu)}</h1>
            </div>
            <div className="flex items-center gap-4">
              <button
                onClick={() => setShowForm(!showForm)}
                className="px-4 py-2 bg-white/20 hover:bg-white/30 rounded-lg transition-all duration-200 flex items-center gap-2"
              >
                <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
                </svg>
                <span className="text-sm font-medium">{t('addWorker')}</span>
              </button>
              <LanguageSelector />
            </div>
          </div>
        </header>

        {/* コンテンツエリア */}
        <div className="p-6">
          {/* フォーム */}
          {showForm && (
            <div className="mb-6 animate-slide-up">
              <WorkerForm
                onSuccess={() => {
                  setShowForm(false);
                }}
              />
            </div>
          )}

          {/* ダッシュボードビュー */}
          {activeMenu === 'dashboard' && (
            <div className="space-y-6">
              {/* クイックアクションボタン */}
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <button
                  onClick={() => {
                    setActiveMenu('dashboard');
                    // 管理者向けサマリーを表示
                  }}
                  className="glass rounded-xl p-6 text-left hover:shadow-lg transition-all duration-200 hover:scale-105"
                >
                  <div className="flex items-center gap-3 mb-2">
                    <div className="w-10 h-10 bg-gradient-to-r from-indigo-600 to-purple-600 rounded-lg flex items-center justify-center">
                      <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                      </svg>
                    </div>
                    <h3 className="text-lg font-semibold text-gray-800">管理者向けサマリー</h3>
                  </div>
                  <p className="text-sm text-gray-600">システム全体の統計情報を表示</p>
                </button>

                <button
                  onClick={() => {
                    setActiveMenu('progress');
                  }}
                  className="glass rounded-xl p-6 text-left hover:shadow-lg transition-all duration-200 hover:scale-105"
                >
                  <div className="flex items-center gap-3 mb-2">
                    <div className="w-10 h-10 bg-gradient-to-r from-blue-600 to-cyan-600 rounded-lg flex items-center justify-center">
                      <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                      </svg>
                    </div>
                    <h3 className="text-lg font-semibold text-gray-800">全作業員の進捗</h3>
                  </div>
                  <p className="text-sm text-gray-600">すべての作業員の進捗状況を確認</p>
                </button>

                <button
                  onClick={() => {
                    setActiveMenu('assignment');
                  }}
                  className="glass rounded-xl p-6 text-left hover:shadow-lg transition-all duration-200 hover:scale-105"
                >
                  <div className="flex items-center gap-3 mb-2">
                    <div className="w-10 h-10 bg-gradient-to-r from-orange-600 to-red-600 rounded-lg flex items-center justify-center">
                      <svg className="w-6 h-6 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                      </svg>
                    </div>
                    <h3 className="text-lg font-semibold text-gray-800">訓練メニュー管理</h3>
                  </div>
                  <p className="text-sm text-gray-600">訓練メニューの作成と管理</p>
                </button>
              </div>

              {/* 作業員リストとダッシュボード */}
              <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                <div className="animate-fade-in">
                  <WorkerList
                    onSelectWorker={setSelectedWorker}
                    selectedWorker={selectedWorker}
                  />
                </div>
                <div className="animate-fade-in">
                  {selectedWorker ? (
                    <IntegratedDashboard workerId={selectedWorker} />
                  ) : (
                    <div className="glass rounded-2xl p-12 text-center card-hover">
                      <div className="w-24 h-24 mx-auto mb-4 bg-gradient-to-br from-blue-100 to-indigo-100 rounded-full flex items-center justify-center">
                        <svg className="w-12 h-12 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                        </svg>
                      </div>
                      <p className="text-gray-600 text-lg font-medium">{t('selectWorker')}</p>
                    </div>
                  )}
                </div>
              </div>

              {/* 管理者向けサマリー */}
              <div className="animate-fade-in">
                <AdminSummary />
              </div>

              {/* 訓練メニュー管理 */}
              <div className="animate-fade-in">
                <TrainingMenuManagement />
              </div>
            </div>
          )}

          {/* その他のメニューコンテンツ */}
          {activeMenu !== 'dashboard' && (
            <div className="space-y-6">
              {activeMenu === 'progress' && selectedWorker !== null && (
                <ProgressManagement workerId={selectedWorker} />
              )}
              {activeMenu === 'japanese' && selectedWorker !== null && (
                <JapaneseProficiencyManagement workerId={selectedWorker} />
              )}
              {activeMenu === 'skill' && selectedWorker !== null && (
                <SkillTrainingManagement workerId={selectedWorker} />
              )}
              {activeMenu === 'milestone' && selectedWorker !== null && (
                <MilestoneManagement workerId={selectedWorker} />
              )}
              {activeMenu === 'career' && selectedWorker !== null && (
                <CareerPathTimeline workerId={selectedWorker} />
              )}
              {activeMenu === 'report' && selectedWorker !== null && (
                <EvidenceReport workerId={selectedWorker} />
              )}
              {activeMenu === 'sessions' && selectedWorker !== null && (
                <TrainingSessionDetail workerId={selectedWorker} />
              )}
              {activeMenu === 'assignment' && selectedWorker !== null && (
                <TrainingMenuAssignmentComponent workerId={selectedWorker} />
              )}
              {activeMenu === 'simulator' && selectedWorker !== null && (
                <ConstructionSimulatorManagement workerId={selectedWorker} />
              )}
              {activeMenu === 'integrated-growth' && selectedWorker !== null && (
                <IntegratedGrowthDashboard workerId={selectedWorker} />
              )}
              {activeMenu === 'specific-skill' && selectedWorker !== null && (
                <SpecificSkillTransitionManagement workerId={selectedWorker} />
              )}
              {activeMenu === 'career-goal' && selectedWorker !== null && (
                <CareerGoalManagement workerId={selectedWorker} />
              )}
              {selectedWorker === null && (
                <div className="glass rounded-2xl p-12 text-center card-hover">
                  <div className="w-24 h-24 mx-auto mb-4 bg-gradient-to-br from-blue-100 to-indigo-100 rounded-full flex items-center justify-center">
                    <svg className="w-12 h-12 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                    </svg>
                  </div>
                  <p className="text-gray-600 text-lg font-medium">{t('selectWorker')}</p>
                </div>
              )}
            </div>
          )}
        </div>
      </main>
    </div>
  );
}

export default App;
